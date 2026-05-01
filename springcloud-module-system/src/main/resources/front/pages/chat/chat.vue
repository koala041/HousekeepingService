<template>
	<view class="service-chat-page">
		<view class="chat-hero">
			<view class="hero-badge">家政服务助手</view>
			<view class="hero-title">您好，我是暖心客服</view>
			<view class="hero-subtitle">可咨询保洁、保姆、家电清洗、上门时间、订单进度与售后问题。</view>
		</view>

		<view class="chat-list" :style="{'padding-bottom': (tabBarHeight + 150) + 'px'}">
			<view v-for="(item,index) in list" :key="index" v-if="item.type!=4" class="message-block">
				<view v-if="item.addtime" class="time-text">{{timeFormat(item.addtime)}}</view>
				<view v-if="item.ask" class="message-row message-user">
					<view class="bubble user-bubble">
						<text v-if="item.type==1">{{plainText(item.ask)}}</text>
						<image :src="fileUrl(item.ask)" v-else-if="item.type==2" mode="aspectFill" class="media-img" @click="imgView(item.ask)"></image>
						<video :src="fileUrl(item.ask)" v-else-if="item.type==3" class="media-video" controls></video>
					</view>
					<image class="avatar" :src="avatarUrl(item.uimage, true)" />
				</view>
				<view v-if="item.reply || item.isTyping" class="message-row message-service">
					<image class="avatar" :src="avatarUrl(item.uimage, false)" />
					<view class="bubble service-bubble">
						<text v-if="item.type==1">{{plainText(item.displayReply || item.reply)}}</text>
						<text v-if="item.isTyping" class="typing-caret">|</text>
						<image :src="fileUrl(item.reply)" v-else-if="item.type==2" mode="aspectFill" class="media-img" @click="imgView(item.reply)"></image>
						<video :src="fileUrl(item.reply)" v-else-if="item.type==3" class="media-video" controls></video>
					</view>
				</view>
				<view v-if="item.reply && shouldShowSuggestions(index)" class="inline-suggest">
					<view class="suggest-card">
						<view class="quick-head">
							<text>猜您想问</text>
							<text class="quick-refresh" @tap="refreshQuestions">{{suggestLoading ? '生成中' : '换一换'}}</text>
						</view>
						<view class="quick-questions">
							<view v-if="suggestLoading && !quickQuestions.length" class="quick-item loading">正在根据对话生成问题...</view>
							<view class="quick-item" v-for="question in quickQuestions" :key="question" @tap="quickAsk(question)">{{ question }}</view>
						</view>
					</view>
				</view>
			</view>
		</view>

		<view class="input-bar" :style="{'bottom':showType?((118 + tabBarHeight) + 'px'):(tabBarHeight + 'px')}">
			<image class="more-icon" src="/static/jiahao.png" mode="widthFix" @click="showClick"></image>
			<input v-model="ask" class="chat-input" :adjust-position="false" :focus="false" maxlength="300" cursor-spacing="10" placeholder="请描述您的家政服务需求" confirm-type="send" @confirm="onSendTap"></input>
			<button @tap="onSendTap" class="send-btn">发送</button>
		</view>
		<view class="tool-panel" v-if="showType" :style='{"bottom":tabBarHeight + "px"}'>
			<view class="tool-item" @click="changeImg">选择图片</view>
			<view class="tool-item" @click="changeVideo">选择视频</view>
			<view class="tool-item" @click="changeClick">转{{askType==1?'人工服务':'智能回复'}}</view>
		</view>
	</view>
</template>

<script>
	import timeMethod from '@/utils/timeMethod'
	export default {
		data() {
			return {
				list: [],
				ask: '',
				showType: false,
				tabBarHeight: 0,
				userid: 0,
				askType: 1,
				typingTimer: null,
				lastAsk: '',
				lastReply: '',
				suggestIndex: 0,
				suggestLoading: false,
				quickQuestions: ['保洁怎么预约？', '可以线上预约保姆吗？', '下单后可以临时取消预约吗？' ]
			};
		},
		async onShow(){
			uni.getSystemInfo({
				success: (info) => {
					this.tabBarHeight = info.windowBottom
				}
			});
			await this.init()
			if(this.list.length==0) {
				this.intelligenceSave('您好，欢迎来到家政服务中心。我可以帮您查询订单、预约保洁、说明收费和售后流程，请直接告诉我您的需求。')
			}
		},
		onHide() {
			if(this.typingTimer) clearInterval(this.typingTimer)
			uni.closeSocket()
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
		},
		methods: {
			fileUrl(path) {
				if(!path) return ''
				const value = String(path).split(',')[0]
				if(value.indexOf('http') === 0 || value.indexOf('/') === 0) return value
				return this.baseUrl + value
			},
			avatarUrl(path, isUser) {
				if(path) return this.fileUrl(path)
				if(isUser) return '/static/gen/upload.png'
				return '/static/service.png'
			},
			plainText(text) {
				return this.stripMarkdown(text || '')
			},
			stripMarkdown(text) {
				return String(text)
					.replace(/```[\s\S]*?```/g, '')
					.replace(/`([^`]*)`/g, '$1')
					.replace(/!\[[^\]]*\]\([^)]*\)/g, '')
					.replace(/\[([^\]]*)\]\([^)]*\)/g, '$1')
					.replace(/(#{1,6}\s*)/g, '')
					.replace(/[*_~>\-]{1,}/g, '')
					.replace(/\n{3,}/g, '\n\n')
					.trim()
			},
			buildHomeServiceReply(ask) {
				const text = this.stripMarkdown(ask).toLowerCase()
				if(/订单|进度|状态|派单/.test(text)) return '您可以在我的服务订单中查看订单状态。若显示待服务，说明服务人员已接单或正在确认上门；若需要催单，请提供预约编号，我会优先帮您核对。'
				if(/改|修改|时间|上门|预约/.test(text)) return '可以协助调整上门时间。建议您提前至少2小时联系服务人员或客服，并说明新的日期、时段和服务地址，避免影响阿姨或师傅排班。'
				if(/保洁|打扫|清洁|深度/.test(text)) return '日常保洁通常包含地面、桌面、厨房台面、卫生间基础清洁；深度保洁会增加油污、水垢、边角等重点处理。您可以告诉我面积、房型和期望时间，我帮您判断更合适的服务。'
				if(/费用|价格|收费|多少钱/.test(text)) return '费用以服务项目页面展示为准，通常会结合服务类型、面积、时长和上门距离计算。下单前请确认项目费用、服务内容和预约时段，支付后平台会保留订单记录。'
				if(/售后|不满意|退款|投诉/.test(text)) return '如果服务后不满意，请先在服务订单中提交服务反馈，说明问题并上传图片。平台会根据订单记录、服务内容和双方沟通情况尽快处理。'
				return '我已收到您的需求。为了更准确帮助您，请补充服务类型、上门地址、期望日期时段和联系电话。家政服务人员会根据订单信息安排接单与上门服务。'
			},
			parseRecommendQuestions(text) {
				const source = this.stripMarkdown(text)
				let arr = []
				try {
					const json = JSON.parse(source.match(/\[[\s\S]*\]/)?.[0] || source)
					if(Array.isArray(json)) arr = json
				} catch(e) {}
				if(!arr.length) {
					arr = source.split(/[\n；;]/).map(item => item.replace(/^\s*\d+[\.、)]\s*/, '').trim())
				}
				return arr.filter(item => item && item.length <= 30).slice(0, 3)
			},
			async buildKnowledgeContext(ask) {
				let res = await this.$api.page('chathelper', { limit: 8, ask: '%' + ask + '%' })
				const rows = res.code == 0 && res.data ? (res.data.list || []) : []
				return rows.map((item, index) => `${index + 1}. 问：${this.stripMarkdown(item.ask)} 答：${this.stripMarkdown(item.reply)}`).join('\n')
			},
			async generateRecommendQuestions(context = '', reply = '') {
				if(this.suggestLoading) return
				this.suggestLoading = true
				this.quickQuestions = []
				try {
					const knowledge = await this.buildKnowledgeContext(context || this.lastAsk || reply)
					const prompt = `你是家政服务O2O平台智能客服。请根据用户问题、客服回答和知识库内容，实时生成3个用户下一步最可能追问的问题。要求：只返回JSON数组，数组内正好3个中文短问题，不要Markdown，不要解释。\n用户问题：${context || this.lastAsk}\n客服回答：${reply || this.lastReply}\n知识库：${knowledge || '暂无匹配知识库'}`
					let res = await this.$api.postPublic('deepseek/askai', { ask: prompt })
					let questions = this.parseRecommendQuestions(res.code == 0 ? res.data : '')
					if(!questions.length) questions = this.makeQuestionsFromText(`${context} ${reply}`)
					this.quickQuestions = questions.slice(0, 3)
				} catch(e) {
					this.quickQuestions = this.makeQuestionsFromText(`${context} ${reply}`).slice(0, 3)
				}
				this.suggestLoading = false
				this.$forceUpdate()
			},
			makeQuestionsFromText(text) {
				const words = this.stripMarkdown(text).replace(/[，。！？、,.!?]/g, ' ').split(/\s+/).filter(Boolean)
				const key = words.find(item => item.length >= 2 && item.length <= 8) || '这个服务'
				return [`${key}需要怎么预约`, `${key}费用怎么确认`, `${key}后续如何处理`]
			},
			refreshQuestions() {
				if(this.suggestLoading) return
				this.suggestIndex++
				this.generateRecommendQuestions(this.lastAsk, `${this.lastReply}\n请换一组角度：${this.suggestIndex}`)
			},
			shouldShowSuggestions(index) {
				for (let i = this.list.length - 1; i >= 0; i--) {
					if (this.list[i].reply && !this.list[i].isTyping) return i === index
				}
				return false
			},
			formatMessages(messages) {
				let lastTime = null;
				messages.forEach((message) => {
					if(message.ask) message.ask = this.stripMarkdown(message.ask)
					if(message.reply) message.reply = this.stripMarkdown(message.reply)
					const currentTime = new Date(message.addtime).getTime();
					if (lastTime !== null) {
						const timeDiff = (currentTime - lastTime) / 1000 / 60;
						if (timeDiff < 3) message.addtime = '';
					}
					lastTime = currentTime;
				});
				return messages;
			},
			timeFormat(time) {
				const Time = timeMethod.getTime(time).split('T');
				const week = timeMethod.getDateToWeek(time);
				const nti = timeMethod.setTimeZero(timeMethod.getNowTime());
				const mnti = timeMethod.setTimeZero(timeMethod.getTime(time));
				const diffDate = timeMethod.calculateTime(nti, mnti);
				const fwnti = timeMethod.setTimeZero(timeMethod.countDateStr(-timeMethod.getDateToWeek(timeMethod.getNowTime()).weekID + 1));
				const diffWeek = timeMethod.calculateTime(mnti, fwnti);
				if (diffDate === 0) return Time[1].slice(0, 5);
				if (diffDate < 172800000) return '昨天 ' + Time[1].slice(0, 5);
				if (diffWeek >= 0) return week.weekName;
				return Time[0].slice(5, 10);
			},
			showClick() {
				this.showType = !this.showType
				this.scrollBottom()
			},
			quickAsk(text) {
				this.ask = text
				this.onSendTap()
			},
			changeImg(){
				this.$api.upload((res) => {
					this.save('upload/' + res.file,2)
				});
			},
			changeVideo(){
				this.$api.uploadMedia((res) => {
					this.save('upload/' + res.file,3)
				});
			},
			changeClick() {
				this.showType = !this.showType
				this.askType = this.askType == 1 ? 2 : 1
				if(this.askType==1) {
					this.intelligenceSave('已切换为智能回复，我会继续为您解答家政预约、订单和售后问题。')
					uni.closeSocket()
				}
				if(this.askType==2) {
					this.intelligenceSave('已为您转人工客服，请描述问题并保持在线。')
					uni.connectSocket({
						url: 'ws' + this.$base.url.split('http')[1] + '/ws?user_id=' + this.userid + '&to_id=1'
					})
					uni.onSocketMessage(() => this.init())
				}
			},
			imgView(img){
				uni.previewImage({ urls: [this.fileUrl(img)] });
			},
			async onSendTap() {
				if(!this.ask || !this.ask.trim()) {
					this.$utils.msg('请输入您要咨询的内容')
					return
				}
				await this.save()
			},
			async intelligenceGet(ask) {
				this.lastAsk = ask
				let res = await this.$api.page('chathelper', { limit: 100, ask: '%' + ask + '%' })
				if (res.code == 0 && res.data.list.length) {
					let arr = this.getRandomNumber(res.data.list)
					this.intelligenceSave(arr.reply)
				} else {
					this.intelligenceGet2(ask)
				}
			},
			async intelligenceGet2(ask) {
				this.ask = '';
				let fallback = this.buildHomeServiceReply(ask)
				try {
					const knowledge = await this.buildKnowledgeContext(ask)
					let res = await this.$api.postPublic('deepseek/askai', {ask: `请作为家政服务O2O平台客服回答用户问题。要求：参考知识库，简洁口语，不使用Markdown；如果知识库没有相关内容，再结合家政服务常识回答。\n用户问题：${ask}\n知识库：${knowledge || '暂无匹配知识库'}`})
					this.intelligenceSave(res.code == 0 && res.data ? res.data : fallback)
				} catch(e) {
					this.intelligenceSave(fallback)
				}
			},
			getRandomNumber(arr) {
				return arr[Math.floor(Math.random() * arr.length)];
			},
			async intelligenceSave(reply) {
				const text = this.stripMarkdown(reply || this.buildHomeServiceReply(''))
				this.lastReply = text
				const typingItem = { reply: '', displayReply: '', userid: Number(this.userid), type: 1, addtime: new Date(), isTyping: true }
				this.list.push(typingItem)
				this.scrollBottom()
				let index = 0
				if(this.typingTimer) clearInterval(this.typingTimer)
				this.typingTimer = setInterval(async () => {
					typingItem.displayReply = text.slice(0, index)
					index++
					this.$forceUpdate()
					this.scrollBottom()
					if(index > text.length) {
						clearInterval(this.typingTimer)
						this.typingTimer = null
						typingItem.isTyping = false
						typingItem.reply = text
						await this.$api.save('chat', { reply: text, userid: Number(this.userid), type: 1 });
						this.init();
						this.generateRecommendQuestions(this.lastAsk, text)
					}
				}, 35)
			},
			async save(asks = null,type=1) {
				const content = asks ? asks : this.stripMarkdown(this.ask)
				this.lastAsk = content
				this.quickQuestions = []
				await this.$api.add('chat', {
					ask: content,
					uimage: uni.getStorageSync('frontHeadportrait'),
					uname: uni.getStorageSync('nickname'),
					type: type,
					userid: this.userid,
					isreply: 1
				});
				if(this.askType==2) uni.sendSocketMessage({ data: content })
				this.ask = '';
				await this.init();
				if (this.askType == 1 && !asks) {
					setTimeout(() => this.intelligenceGet(content), 500)
				}
			},
			async init() {
				this.userid = uni.getStorageSync('appUserid')
				let res = await this.$api.page('chat', { sort: 'addtime', order: 'asc', limit: 1000 });
				this.list = this.formatMessages(res.data.list || []);
				this.syncLatestContext()
				this.scrollBottom()
			},
			syncLatestContext() {
				for(let i = this.list.length - 1; i >= 0; i--) {
					if(this.list[i].reply && !this.lastReply) this.lastReply = this.stripMarkdown(this.list[i].reply)
					if(this.list[i].ask && !this.lastAsk) this.lastAsk = this.stripMarkdown(this.list[i].ask)
					if(this.lastAsk && this.lastReply) break
				}
			},
			scrollBottom(){
				this.$nextTick(() => {
					setTimeout(() => {
						uni.pageScrollTo({ scrollTop: 99999, duration: 0 })
					}, 120)
				})
			},
		}
	}
</script>

<style lang="scss">
	page {
		background: linear-gradient(180deg, #fff1f5 0%, #ffe3ec 46%, #fff8f5 100%);
		padding-bottom: 100upx;
	}
	.service-chat-page {
		min-height: 100vh;
		background: radial-gradient(circle at 16% 8%, #ffffff 0, #ffffff 80rpx, transparent 180rpx), linear-gradient(180deg, #fff1f5 0%, #ffe4ed 100%);
		padding: 24rpx 24rpx 0;
	}
	.chat-hero {
		padding: 32rpx;
		border-radius: 32rpx;
		background: linear-gradient(135deg, #ff7da7 0%, #ffb2c8 54%, #ffe3d7 100%);
		box-shadow: 0 18rpx 42rpx rgba(214, 73, 116, .18);
		color: #fff;
	}
	.hero-badge { display: inline-flex; padding: 8rpx 18rpx; border-radius: 999rpx; background: rgba(255,255,255,.26); font-size: 24rpx; }
	.hero-title { margin-top: 18rpx; font-size: 44rpx; font-weight: 700; letter-spacing: 2rpx; }
	.hero-subtitle { margin-top: 12rpx; font-size: 26rpx; line-height: 1.6; opacity: .96; }
	.inline-suggest { display: flex; padding-left: 88rpx; margin: 8rpx 0 22rpx; }
	.suggest-card { max-width: 78%; padding: 22rpx; border-radius: 24rpx; background: rgba(255,255,255,.92); box-shadow: 0 10rpx 28rpx rgba(216, 87, 126, .1); border: 1rpx solid rgba(255, 141, 176, .18); }
	.quick-head { display: flex; align-items: center; justify-content: space-between; color: #7d4a5c; font-size: 28rpx; font-weight: 700; }
	.quick-refresh { padding: 8rpx 18rpx; border-radius: 999rpx; color: #d94f7b; background: #fff1f5; font-size: 24rpx; font-weight: 400; }
	.quick-questions {
		display: flex;
		flex-direction: column; /* 改成纵向排列 */
		gap: 12rpx; /* 问题之间间距 */
		padding: 18rpx 0 0;
	}
	.quick-item {
		width: 100%; /* 一行铺满 */
		padding: 18rpx 24rpx;
		border-radius: 16rpx;
		background: #fff;
		color: #d94f7b;
		font-size: 26rpx;
		box-shadow: 0 4rpx 12rpx rgba(216, 87, 126, .08);
		box-sizing: border-box;
		text-align: left;
	}
	.chat-list { padding-top: 4rpx; }
	.time-text { text-align: center; font-size: 22rpx; color: #bc8b9b; margin: 18rpx 0; }
	.message-row { display: flex; align-items: flex-start; margin: 18rpx 0; }
	.message-user { justify-content: flex-end; }
	.message-service { justify-content: flex-start; }
	.avatar { width: 72rpx; height: 72rpx; border-radius: 50%; object-fit: cover; background: #fff; box-shadow: 0 6rpx 18rpx rgba(210, 76, 118, .12); }
	.bubble { max-width: 72%; padding: 22rpx 26rpx; border-radius: 28rpx; font-size: 29rpx; line-height: 1.7; word-break: break-word; }
	.user-bubble { margin-right: 16rpx; color: #fff; background: linear-gradient(135deg, #ff6f9f, #ff9ab9); border-bottom-right-radius: 8rpx; }
	.service-bubble { margin-left: 16rpx; color: #57404a; background: rgba(255,255,255,.96); border: 1rpx solid rgba(255, 141, 176, .18); border-bottom-left-radius: 8rpx; box-shadow: 0 10rpx 28rpx rgba(218, 98, 133, .1); }
	.typing-caret { color: #ff6f9f; animation: caret 1s steps(2, start) infinite; }
	@keyframes caret { 50% { opacity: 0; } }
	.media-img, .media-video { width: 300rpx; border-radius: 18rpx; }
	.input-bar { position: fixed; left: 20rpx; right: 20rpx; display: flex; align-items: center; padding: 16rpx; border-radius: 999rpx; background: rgba(255,255,255,.96); box-shadow: 0 -8rpx 30rpx rgba(206, 72, 113, .16); z-index: 9; }
	.more-icon { width: 46rpx; margin: 0 14rpx 0 4rpx; }
	.chat-input { flex: 1; height: 72rpx; padding: 0 24rpx; border-radius: 999rpx; background: #fff5f8; color: #543742; font-size: 28rpx; }
	.send-btn { margin-left: 14rpx; width: 128rpx; height: 72rpx; line-height: 72rpx; border-radius: 999rpx; background: #ff6f9f; color: #fff; font-size: 28rpx; box-shadow: 0 8rpx 20rpx rgba(255, 111, 159, .28); }
	.tool-panel { position: fixed; left: 20rpx; right: 20rpx; display: flex; gap: 16rpx; padding: 18rpx; border-radius: 28rpx; background: #fff; box-shadow: 0 -8rpx 24rpx rgba(206, 72, 113, .12); z-index: 8; }
	.tool-item { flex: 1; line-height: 72rpx; border-radius: 20rpx; color: #d94f7b; background: #fff1f5; text-align: center; font-size: 26rpx; }
</style>
