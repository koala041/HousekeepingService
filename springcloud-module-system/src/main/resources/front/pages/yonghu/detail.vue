
<template>
<view>
<mescroll-uni @init="mescrollInit" :up="upOption" :down="downOption" @down="downCallback" @up="upCallback" @scroll="scrollChange">
	<view class="content">
		<view class="container" :style='{"minHeight":"100%","width":"100%","padding":"24rpx","position":"relative","background":"#fff","height":"auto"}'>
			<swiper :style='{"width":"100%","height":"440rpx"}' class="swiper" :indicator-dots='false' :autoplay='false' :circular='false' indicator-active-color='#000000' indicator-color='rgba(0, 0, 0, .3)' :duration='500' :interval='5000' :vertical='false'>
				<swiper-item :style='{"width":"100%","borderRadius":"12rpx","background":"#fff","height":"440rpx"}' v-for="(swiper,index) in swiperList" :key="index">
					<img :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"440rpx"}' v-if="swiper.substring(0,4)=='http'" :src="swiper" @tap="imgView(swiper)">
					<img :style='{"width":"100%","objectFit":"cover","borderRadius":"12rpx","display":"block","height":"440rpx"}' v-else :src="baseUrl+swiper" @tap="imgView(baseUrl+swiper)">
				</swiper-item>
			</swiper>
			<view :style='{"alignContent":"flex-start","padding":"24rpx 0 24rpx 0","alignItems":"flex-start","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","height":"auto"}' class="detail-content">


				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>用户账号：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yonghuzhanghao}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>用户姓名：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.yonghuxingming}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>性别：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' >{{detail.xingbie}}</view>
				</view>
				<view class="detail-list-item" :style='{"padding":"0 20rpx 0 0px","margin":"0 0 12rpx 0","borderColor":"#25252530","borderRadius":"20rpx","borderWidth":"0px","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}'>
					<view class="lable" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"48rpx","fontSize":"28rpx","fontWeight":"500"}'>手机号：</view>
					<view class="text" :style='{"padding":"0px","margin":"0px","lineHeight":"48rpx","fontSize":"28rpx","color":"#666","flex":"1"}' style="text-decoration: underline" @tap="callClick(detail.mobile)" >{{detail.mobile}}</view>
				</view>








				<view class="bottom-content bg-white tabbar border shop" :style='{"padding":"0","margin":"20rpx 0 0 0","flexWrap":"wrap","background":"none","display":"flex","width":"100%","height":"auto","order":"60"}'>

					<button :style='{"border":"2rpx solid #c0755410","padding":"0 20rpx","margin":"0 10rpx 10rpx 0","color":"#c07554","borderRadius":"20rpx","background":"#c0755420","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="userid&&isAuth('yonghu','私聊')" @tap="chatClick">私聊</button>
					<button :style='{"border":"2rpx solid #c0755410","padding":"0 20rpx","margin":"0 10rpx 10rpx 0","color":"#c07554","borderRadius":"20rpx","background":"#c0755420","width":"auto","fontSize":"28rpx","minWidth":"120rpx","lineHeight":"80rpx","height":"80rpx"}' v-if="!userid&&isAuthFront('yonghu','私聊')" @tap="chatClick">私聊</button>
				</view>
			</view>
		</view>
	</view>
</mescroll-uni>
	<uni-popup class="popup-content" ref="chatPopup" type="bottom" :maskClick="true" @change="clearChat">
		<view class="popup-chat">
			<scroll-view :scrollTop="scrollTop2" scroll-y="true" style="background: #eee;">
				<view class="chat-content" id="chat-content">
					<view v-bind:key="item.id" v-for="item in chatList">
						<div v-if="item.addtime" style="width: 100%;text-align: center;font-size: 10px;color: #666;">{{timeFormat(item.addtime)}}</div>
						<view v-if="item.uid==user.id" class="right-content">
							<view v-if="item.format==1" class="text-content">{{item.content}}</view>
							<img v-else-if="item.format==2" :src="item.content?baseUrl + item.content:''" @click="preViewImg(baseUrl + item.content)"
								style="width: 100px;height: 100px;object-fit: cover;" />
							<video :src="baseUrl + item.content" v-else-if="item.format==3" style="width: 150px;" controls></video>
							<view v-else-if="item.format==5" class="voice-content" @click="playVoice(baseUrl + item.content,item.id)">
								<text class="voice-icon">{{playingAudioId != item.id ? '🔊' : '⏸'}}</text>
							</view>
							<img :src="mpic?baseUrl + mpic:''" alt=""
								style="width: 40px;border-radius: 50%;height: 40px;margin: 0 0 0 10px;object-fit: cover;" />
						</view>
						<view v-else class="left-content">
							<img :src="fpic?baseUrl + fpic:''" alt=""
								style="width: 40px;border-radius: 50%;height: 40px;margin: 0 10px 0 0;object-fit: cover;" />
							<view v-if="item.format==1" class="text-content">{{item.content}}</view>
							<img v-else-if="item.format==2" :src="item.content?baseUrl + item.content:''" @click="preViewImg(baseUrl + item.content)"
								style="width: 100px;height: 100px;object-fit: cover;" />
							<video :src="baseUrl + item.content" v-else-if="item.format==3" style="width: 150px;" controls></video>
							<view v-else-if="item.format==5" class="voice-content" @click="playVoice(baseUrl + item.content,item.id)">
								<text class="voice-icon">{{playingAudioId != item.id ? '🔊' : '⏸'}}</text>
							</view>
						</view>
						<view class="clear-float"></view>
					</view>
				</view>
			</scroll-view>
			<view style="">
				<input v-model="chatForm.content" placeholder="请输入内容" confirm-type="send" @confirm="addChat"
					style="width: calc(100% - 20px);font-size: 14px;background: #eee;margin: 0 10px 10px;padding: 0 10px;">
				</input>
				<view style="display: flex;justify-content: space-around;">
					<button @click="addChat" style="width: auto;padding: 0 10px;background: #ff6f9f;font-size: 13px;border: none;color: #fff;">发送</button>
					<button class="voice-btn" :class="isRecording ? 'recording' : ''" @click="toggleRecord">
						{{isRecording ? '停止录音(' + recordDuration + 's)' : '开始录音'}}
					</button>
					<button @click="uploadSuccess" style="width: auto;padding: 0 10px;background: #ff9ab9;font-size: 13px;border: none;color: #fff;">上传图片</button>
					<button @click="uploadSuccess2" style="width: auto;padding: 0 10px;background: #ff9ab9;font-size: 13px;border: none;color: #fff;">上传视频</button>
				</view>
				
			</view>
		</view>
	</uni-popup>
</view>
</template>

<script>
	import timeMethod from '@/utils/timeMethod'
	export default {
		data() {
			return {
				btnColor: ['#409eff','#67c23a','#909399','#e6a23c','#f56c6c','#356c6c','#351c6c','#f093a9','#a7c23a','#104eff','#10441f','#a21233','#503319'],
				id: '',
				userid: '',
				detail: {},
				swiperList: [],
				commentList: [],
				mescroll: null, //mescroll实例对象
				downOption: {
					auto: false //是否在初始化后,自动执行下拉回调callback; 默认true
				},
				upOption: {
					noMoreSize: 3, //如果列表已无数据,可设置列表的总数量要大于半页才显示无更多数据;避免列表数据过少(比如只有一条数据),显示无更多数据会不好看; 默认5
					textNoMore: '~ 没有更多了 ~',
					onScroll: true,
					toTop: true
				},
				hasNext: true,
				user: {},
				count: 0,
				timer: null,
				title:'',
				chatList: [],
				scrollTop2: 0,
				chatForm: {
					content: ''
				},
				fpic: '',
				fname: '',
				fid: '',
				mpic: uni.getStorageSync('frontHeadportrait'),
				// 语音聊天
				recorderManager: null,
				isRecording: false,
				recordDuration: 0,
				recordTimer: null,
				playingAudioId: null,
				innerAudioContext: null,
				// H5环境录音相关
				mediaRecorder: null,
				audioChunks: [],
				audioStream: null,
				scrollTop: 0,
			}
		},
		components: {
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			username() {
				return uni.getStorageSync("nickname")
			},
		},
		async onLoad(options) {
			// #ifdef APP-PLUS
			let page = getCurrentPages()
			this.href = this.baseUrl + 'front/h5/#/' + page[page.length - 1].route
			// #endif
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.id = options.id;
			if(options.userid) {
				this.userid = options.userid;
			}
			// 渲染数据
			this.init();
		},
		// #ifdef MP-WEIXIN
		onShareAppMessage(){
			var obj = {
				title: this.title,
				imageUrl: this.swiperList[0]?this.baseUrl + this.swiperList[0]: ''
			}
			return obj
		},
		// #endif
		onUnload() {
			if(this.timer) {
				clearInterval(this.timer);
			}
		},
		async onShow(options) {
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			this.btnColor = this.btnColor.sort(()=> {
				return (0.5-Math.random());
			});
			let crossCleanType = uni.getStorageSync('crossCleanType')
			if(crossCleanType) {
				uni.removeStorageSync('crossCleanType')
				res = await this.$api.info('yonghu', this.id);
				let reg=new RegExp('http://localhost:8080/springcloud-alibaba/upload','g')//g代表全部
				this.detail = res.data;
				this.title = this.detail.touxiang
			}
		},
		destroyed: function() {
			//window.clearInterval(this.inter);
		},
		methods: {
			scrollChange(e){
				this.scrollTop = e.scrollTop
			},
			scrollTopClick(){
				uni.pageScrollTo({
					scrollTop: 0
				})
			},
			imgView(url){
				let arr = []
				for(let x in this.swiperList){
					arr.push(this.swiperList[x].substr(0,4)=='http'?this.swiperList[x]:this.baseUrl + this.swiperList[x])
				}
				uni.previewImage({
					current: url,
					urls: arr
				})
			},
			// 拨打电话
			callClick(row){
				uni.makePhoneCall({
					phoneNumber: row
				})
			},
			// 支付
			onPayTap(){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				uni.setStorageSync('paytable','yonghu');
				uni.setStorageSync('payObject',this.detail);
				this.$utils.jump('../pay-confirm/pay-confirm?type=1')
			},
			onDetailTap(item) {
				uni.setStorageSync("useridTag",this.userid);
				this.$utils.jump(`./detail?id=${item.id}&userid=`+this.userid)
			},
			// 跨表
			async onAcrossTap(tableName,crossOptAudit,crossOptPay,statusColumnName,tips,statusColumnValue,type=1){
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				uni.setStorageSync('crossTable','yonghu');
				uni.setStorageSync(`crossObj`, this.detail);
				uni.setStorageSync(`statusColumnName`, statusColumnName);
				uni.setStorageSync(`statusColumnValue`, statusColumnValue);
				uni.setStorageSync(`tips`, tips);
				if(statusColumnName!=''&&!statusColumnName.startsWith("[")) {
					var obj = uni.getStorageSync('crossObj');
					for (var o in obj){
						if(o==statusColumnName && obj[o]==statusColumnValue){
							this.$utils.msg(tips);
							return
						}
					}
				}
				this.$utils.jump(`../${tableName}/add-or-update?cross=true`);
			},
			// 获取详情
			async init(type=1){
				if(this.timer) {
					clearInterval(this.timer);
				}
				let res = await this.$api.info('yonghu', this.id);
				if (res.data.touxiang) {
					this.fpic = res.data.touxiang.split(',')[0]
				} else if (res.data.headportrait) {
					this.fpic = res.data.headportrait.split(',')[0]
				}
				if(res.data.yonghuzhanghao){
					this.fname = res.data.yonghuzhanghao
				}
				this.fid = res.data.id
				let reg=new RegExp('http://localhost:8080/springcloud-alibaba/upload','g')//g代表全部
				this.detail = res.data;

				this.title = this.detail.touxiang




				// 轮播图片
				this.swiperList = this.detail.touxiang ? this.detail.touxiang.split(",") : [];
				



				if(type==2){
					this.detail.discussnum++
					await this.$api.update('yonghu',this.detail)
				}
			},
			// mescroll组件初始化的回调,可获取到mescroll对象
			mescrollInit(mescroll) {
				this.mescroll = mescroll;
			},

			/*下拉刷新的回调 */
			downCallback(mescroll) {
				this.hasNext = true
				mescroll.resetUpScroll()
			},

			/*上拉加载的回调: mescroll携带page的参数, 其中num:当前页 从1开始, size:每页数据条数,默认10 */
			async upCallback(mescroll) {
				mescroll.endSuccess(mescroll.size, this.hasNext);

			},



			onChatTap() {
				this.$utils.jump('../chat/chat')
			},
			// 下载
			download(url ){
				if(!url){
					return false
				}
				let _this = this;
				url=_this.$base.url +  url;
				uni.downloadFile({
					url: url,
					success: (res) => {
						if (res.statusCode === 200) {
							_this.$utils.msg('下载成功');
							// #ifdef H5
							 window.open(url);
							// #endif
							// #ifndef H5
							uni.saveFile({
								tempFilePath: res.tempFilePath, //临时路径
								success: function(obj) {
									uni.showToast({
										icon: 'success',
										mask: true,
										title: '下载成功' , 
										duration: 2000,
									});
									setTimeout(() => {
										console.log('obj.savedFilePath',obj.savedFilePath);
										var filePath = obj.savedFilePath;
										uni.openDocument({ //新开页面打开文档，支持格式：doc, xls, ppt, pdf, docx, xlsx, pptx。
											filePath: filePath,
											showMenu: true,
											success: function(res) {
												console.log('打开文档成功');
											}
										});
									}, 2000)
								}
							});
							// #endif
						}
					}
				});
			},
			//
			onCartTabTap() {
				this.$utils.tab('../shop-cart/shop-cart')
			},
			chatClick() {
				let that = this
				if(!this.user){
					this.$utils.msg("请先登录")
					setTimeout(()=>{
						that.$utils.jump('../login/login')
					},1500)
					return false
				}
				if(this.detail.id==this.user.id){
					this.$utils.msg('不能给自己发信息！');
					return false
				}
				// 初始化音频播放器（如果未初始化）
				if (!this.innerAudioContext) {
					this.innerAudioContext = uni.createInnerAudioContext();
					this.innerAudioContext.onEnded(() => {
						this.playingAudioId = null
					});
					this.innerAudioContext.onError((err) => {
						this.playingAudioId = null
						this.$utils.msg('播放失败')
					});
				}
				this.getChatList()
				uni.connectSocket({
					url: 'ws' + this.$base.url.split('http')[1] + '/ws?user_id=' + uni.getStorageSync("appUserid") + '&to_id=' + this.fid
				})
				uni.onSocketMessage((res)=> {
					this.getChatList()
				})
				this.$refs.chatPopup.open()
			},
			formatMessages(messages) {
				let lastTime = null;
				messages.forEach((message, index) => {
					const currentTime = new Date(message.addtime).getTime();
					if (lastTime !== null) {
						const timeDiff = (currentTime - lastTime) / 1000 / 60; // 转换为分钟
						if (timeDiff < 3) {
							message.addtime = ''; // 如果小于3分钟，不显示时间
						}
					}
					lastTime = currentTime;
				});
				return messages;
			},
			timeFormat(time) {
				const Time = timeMethod.getTime(time).split("T");
				//当前消息日期属于周
				const week = timeMethod.getDateToWeek(time);
				//当前日期0时
				const nti = timeMethod.setTimeZero(timeMethod.getNowTime());
				//消息日期当天0时
				const mnti = timeMethod.setTimeZero(timeMethod.getTime(time));
				//计算日期差值
				const diffDate = timeMethod.calculateTime(nti, mnti);
				//本周一日期0时 （后面+1是去除当天时间）
				const fwnti = timeMethod.setTimeZero(timeMethod.countDateStr(-timeMethod.getDateToWeek(timeMethod
					.getNowTime()).weekID + 1));
				//计算周日期差值
				const diffWeek = timeMethod.calculateTime(mnti, fwnti);
			
				if (diffDate === 0) { //消息发送日期减去当天日期如果等于0则是当天时间
					return Time[1].slice(0, 5);
				} else if (diffDate < 172800000) { //当前日期减去消息发送日期小于2天（172800000ms）则是昨天-  一天最大差值前天凌晨00:00:00到今天晚上23:59:59
					return "昨天 " + Time[1].slice(0, 5);
				} else if (diffWeek >= 0) { //消息日期减去本周一日期大于0则是本周
					return week.weekName;
				} else { //其他时间则是日期
					return Time[0].slice(5, 10);
				}
			},
			async getChatList() {
				this.scrollTop2 = 0
				let res = await this.$api.mlist('chatmessage',{
					page: 1,
					limit: 1000,
					uid: this.user.id,
					fid: this.fid
				})
				this.chatList = this.formatMessages(res.data.list)
				setTimeout(() => {
					this.scrollTop2 = 1000000
				}, 100)
				
			},
			clearChat(e) {
				if(!e.show){
					uni.closeSocket()
				}
			},
			// 语音聊天功能 - 切换录音状态
			toggleRecord() {
				// 检查是否已选择聊天对象
				if (!this.fid || !this.user || !this.user.id) {
					this.$utils.msg('请先选择聊天对象')
					return;
				}
				
				if (this.isRecording) {
					// 如果正在录音，则停止
					this.stopRecord();
				} else {
					// 如果未录音，则开始
					this.startRecord();
				}
			},
			startRecord() {
				// #ifdef H5
				// H5环境使用浏览器MediaRecorder API
				this.startRecordH5();
				// #endif
				// #ifndef H5
				// 非H5环境使用uni-app录音管理器
				this.startRecordNative();
				// #endif
			},
			// H5环境录音
			async startRecordH5() {
				try {
					// 检查浏览器是否支持MediaRecorder
					if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
						this.$utils.msg('您的浏览器不支持录音功能')
						return;
					}
					
					// 获取麦克风权限
					const stream = await navigator.mediaDevices.getUserMedia({ audio: true });
					this.audioStream = stream;
					this.audioChunks = [];
					
					// 创建MediaRecorder
					const mimeType = MediaRecorder.isTypeSupported('audio/webm') ? 'audio/webm' : 
									MediaRecorder.isTypeSupported('audio/mp4') ? 'audio/mp4' : 
									'audio/webm';
					
					this.mediaRecorder = new MediaRecorder(stream, {
						mimeType: mimeType
					});
					
					this.mediaRecorder.ondataavailable = (event) => {
						if (event.data.size > 0) {
							this.audioChunks.push(event.data);
						}
					};
					
					this.mediaRecorder.onstop = () => {
						const audioBlob = new Blob(this.audioChunks, { type: this.mediaRecorder.mimeType });
						const audioUrl = URL.createObjectURL(audioBlob);
						
						// 将Blob转换为File对象用于上传
						const file = new File([audioBlob], 'recording.' + (mimeType.includes('webm') ? 'webm' : 'mp4'), {
							type: mimeType
						});
						
						// 停止所有音频轨道
						if (this.audioStream) {
							this.audioStream.getTracks().forEach(track => track.stop());
							this.audioStream = null;
						}
						
						// 上传录音文件
						this.uploadVoiceH5(file);
					};
					
					this.mediaRecorder.onerror = (err) => {
						this.isRecording = false;
						this.recordDuration = 0;
						clearInterval(this.recordTimer);
						if (this.audioStream) {
							this.audioStream.getTracks().forEach(track => track.stop());
							this.audioStream = null;
						}
						this.$utils.msg('录音失败');
					};
					
					// 开始录音
					this.mediaRecorder.start();
					this.isRecording = true;
					this.recordDuration = 0;
					
					// 开始计时
					this.recordTimer = setInterval(() => {
						this.recordDuration = this.recordDuration + 1;
						// 最长60秒
						if (this.recordDuration >= 60) {
							this.stopRecord();
						}
					}, 1000);
					
				} catch (e) {
					this.$utils.msg('录音失败: ' + (e.message || '无法访问麦克风'));
					this.isRecording = false;
					this.recordDuration = 0;
				}
			},
			// 原生环境录音（小程序、APP）
			startRecordNative() {
				try {
					// 检查是否已初始化录音管理器
					if (!this.recorderManager) {
						this.recorderManager = uni.getRecorderManager();
						this.recorderManager.onStart(() => {
							this.isRecording = true
							this.recordDuration = 0
							// 开始计时
							this.recordTimer = setInterval(() => {
								this.recordDuration = this.recordDuration + 1
							}, 1000);
						});
						this.recorderManager.onStop((res) => {
							clearInterval(this.recordTimer);
							this.isRecording = false
							this.recordDuration = 0
							if (res.tempFilePath) {
								this.uploadVoice(res.tempFilePath);
							} else {
								this.$utils.msg('录音文件不存在')
							}
						});
						this.recorderManager.onError((err) => {
							clearInterval(this.recordTimer);
							this.isRecording = false
							this.recordDuration = 0
							this.$utils.msg('录音失败: ' + (err.errMsg || '未知错误'))
						});
					}
					
					// 录音配置参数
					const recordOptions = {
						duration: 60000, // 最长录音时间60秒
						sampleRate: 16000,
						numberOfChannels: 1,
						encodeBitRate: 96000,
						frameSize: 50
					};
					
					// 根据平台设置录音格式
					// #ifdef MP-WEIXIN
					recordOptions.format = 'mp3';
					// 请求录音权限
					uni.authorize({
						scope: 'scope.record',
						success: () => {
							// 开始录音
							try {
								this.recorderManager.start(recordOptions);
							} catch (e) {
								this.$utils.msg('开始录音失败')
							}
						},
						fail: () => {
							uni.showModal({
								title: '提示',
								content: '需要录音权限才能使用语音功能',
								showCancel: false
							});
						}
					});
					// #endif
					// #ifdef APP-PLUS
					recordOptions.format = 'mp3';
					// 开始录音
					try {
						this.recorderManager.start(recordOptions);
					} catch (e) {
						this.$utils.msg('开始录音失败: ' + (e.message || '未知错误'))
					}
					// #endif
					// #ifndef MP-WEIXIN || APP-PLUS
					// 其他平台默认使用aac格式
					recordOptions.format = 'aac';
					// 开始录音
					try {
						this.recorderManager.start(recordOptions);
					} catch (e) {
						this.$utils.msg('开始录音失败: ' + (e.message || '未知错误'))
					}
					// #endif
				} catch (e) {
					this.$utils.msg('录音功能初始化失败')
				}
			},
			stopRecord() {
				// #ifdef H5
				// H5环境停止录音
				this.stopRecordH5();
				// #endif
				// #ifndef H5
				// 非H5环境停止录音
				this.stopRecordNative();
				// #endif
			},
			// H5环境停止录音
			stopRecordH5() {
				try {
					if (this.isRecording && this.mediaRecorder && this.mediaRecorder.state !== 'inactive') {
						this.mediaRecorder.stop();
					}
					if (this.recordTimer) {
						clearInterval(this.recordTimer);
					}
					this.isRecording = false;
				} catch (e) {
					this.isRecording = false;
					this.recordDuration = 0;
					if (this.recordTimer) {
						clearInterval(this.recordTimer);
					}
					if (this.audioStream) {
						this.audioStream.getTracks().forEach(track => track.stop());
						this.audioStream = null;
					}
				}
			},
			// 原生环境停止录音
			stopRecordNative() {
				try {
					if (this.isRecording && this.recorderManager) {
						this.recorderManager.stop();
					}
				} catch (e) {
					this.isRecording = false
					this.recordDuration = 0
					if (this.recordTimer) {
						clearInterval(this.recordTimer);
					}
				}
			},
			// H5环境上传语音文件
			async uploadVoiceH5(file) {
				let that = this;
				uni.showLoading({
					title: '上传中...',
					mask: true
				});
				
				// 使用FormData上传
				const formData = new FormData();
				formData.append('file', file);
				
				try {
					const response = await fetch(`${this.baseUrl}file/upload`, {
						method: 'POST',
						headers: {
							'Token': uni.getStorageSync("appToken")
						},
						body: formData
					});
					
					const result = await response.json();
					uni.hideLoading();
					
					if (result.code == 0) {
						// 检查好友关系
						let res = await this.$api.page('friend', {
							uid: that.user.id,
							fid: that.fid,
							type: 2
						});
						if (!res.data.list.length) {
							await this.$api.add('friend', {
								uid: that.user.id,
								fid: that.fid,
								name: that.fname,
								picture: that.fpic,
								type: 2,
								tablename: 'yonghu'
							});
							await this.$api.add('friend', {
								fid: that.user.id,
								uid: that.fid,
								name: uni.getStorageSync("nickname"),
								picture: that.mpic,
								type: 2,
								tablename: uni.getStorageSync("nowTable")
							});
						}
						// 发送语音消息
						await this.$api.add('chatmessage', {
							uid: that.user.id,
							fid: that.fid,
							content: 'upload/' + result.file,
							format: 5,
						});
						uni.sendSocketMessage({
							data: 'upload/' + result.file
						});
						that.getChatList();
					} else {
						uni.showToast({
							title: result.msg || '上传失败',
							icon: 'none',
							duration: 2000
						});
					}
				} catch (err) {
					uni.hideLoading();
					uni.showToast({
						title: '上传失败',
						icon: 'none',
						duration: 2000
					});
				}
			},
			// 上传语音文件（原生环境）
			async uploadVoice(tempFilePath) {
				let that = this;
				uni.showLoading({
					title: '上传中...',
					mask: true
				});
				
				// 上传音频文件
				uni.uploadFile({
					url: `${this.baseUrl}file/upload`,
					filePath: tempFilePath,
					name: 'file',
					header: {
						'Token': uni.getStorageSync("appToken")
					},
					success: async (uploadRes) => {
						uni.hideLoading();
						let result = JSON.parse(uploadRes.data);
						if (result.code == 0) {
							// 检查好友关系
							let res = await this.$api.page('friend', {
								uid: that.user.id,
								fid: that.fid,
								type: 2
							});
							if (!res.data.list.length) {
								await this.$api.add('friend', {
									uid: that.user.id,
									fid: that.fid,
									name: that.fname,
									picture: that.fpic,
									type: 2,
									tablename: 'yonghu'
								});
								await this.$api.add('friend', {
									fid: that.user.id,
									uid: that.fid,
									name: uni.getStorageSync("nickname"),
									picture: that.mpic,
									type: 2,
									tablename: uni.getStorageSync("nowTable")
								});
							}
							// 发送语音消息
							await this.$api.add('chatmessage', {
								uid: that.user.id,
								fid: that.fid,
								content: 'upload/' + result.file,
								format: 5,
							});
							uni.sendSocketMessage({
								data: 'upload/' + result.file
							});
							that.getChatList();
						} else {
							uni.showToast({
								title: result.msg || '上传失败',
								icon: 'none',
								duration: 2000
							});
						}
					},
					fail: (err) => {
						uni.hideLoading();
						uni.showToast({
							title: '上传失败',
							icon: 'none',
							duration: 2000
						});
					}
				});
			},
			// 播放语音
			playVoice(audioUrl, audioId) {
				
				// 如果正在播放同一个音频，则停止
				if (this.playingAudioId === audioId) {
					if (this.innerAudioContext) {
						this.innerAudioContext.stop();
						this.innerAudioContext.destroy();
					}
					this.playingAudioId = null
					this.$forceUpdate();
					return;
				}
				
				// 确保URL是完整的
				if (!audioUrl) {
					this.$utils.msg('音频地址无效');
					return;
				}
				
				// 创建新的音频上下文
				this.innerAudioContext = uni.createInnerAudioContext();
				this.innerAudioContext.autoplay = false;
				
				// 绑定事件监听
				this.innerAudioContext.onPlay(() => {
					this.$forceUpdate();
				});
				this.innerAudioContext.onEnded(() => {
					this.playingAudioId = null
					this.$forceUpdate();
				});
				this.innerAudioContext.onError((err) => {
					this.playingAudioId = null
					let errMsg = '播放失败';
					if (err.errMsg) {
						errMsg += ': ' + err.errMsg;
					}
					this.$utils.msg(errMsg);
					this.$forceUpdate();
				});
				this.innerAudioContext.onPause(() => {
					this.$forceUpdate();
				});
				this.innerAudioContext.onStop(() => {
					this.playingAudioId = null
					this.$forceUpdate();
				});
				
				// 设置音频源
				this.innerAudioContext.src = audioUrl;
				this.playingAudioId = audioId
				
				// 使用$nextTick确保在设置src后播放
				this.$nextTick(() => {
					try {
						this.innerAudioContext.play();
						this.$forceUpdate();
					} catch (e) {
						this.playingAudioId = null
						this.$utils.msg('播放失败: ' + (e.message || '未知错误'))
						this.$forceUpdate();
					}
				});
			},
			preViewImg(img){
				uni.previewImage({
					urls: [img],
					current:img
				})
			},
			async uploadSuccess() {
				let _this = this;
				await this.$api.upload(async function(obj) {
					let res = await _this.$api.page('friend', {
						uid: _this.user.id,
						fid: _this.fid,
					})
					if(!res.data.list.length){
						await _this.$api.add('friend',{
							uid: _this.user.id,
							fid: _this.fid,
							name: _this.fname,
							picture: _this.fpic,
							type: 2,
							tablename:  'yonghu',
						})
						await _this.$api.add('friend',{
							fid: _this.user.id,
							uid: _this.fid,
							name: uni.getStorageSync("nickname"),
							picture: _this.mpic,
							type: 2,
							tablename: uni.getStorageSync("nowTable")
						})
					}
					await _this.$api.add('chatmessage',{
						uid: _this.user.id,
						fid: _this.fid,
						content: 'upload/' + obj.file,
						format: 2
					})
					_this.chatForm = {
						content: ''
					}
					uni.sendSocketMessage({
						data: 'upload/' + obj.file
					})
					_this.getChatList()
				})
			},
			async uploadSuccess2() {
				let _this = this;
				await this.$api.uploadMedia(async function(obj) {
					let res = await _this.$api.page('friend', {
						uid: _this.user.id,
						fid: _this.fid,
					})
					if(!res.data.list.length){
						await _this.$api.add('friend',{
							uid: _this.user.id,
							fid: _this.fid,
							name: _this.fname,
							picture: _this.fpic,
							type: 2,
							tablename:  'yonghu',
						})
						await _this.$api.add('friend',{
							fid: _this.user.id,
							uid: _this.fid,
							name: uni.getStorageSync("nickname"),
							picture: _this.mpic,
							type: 2,
							tablename: uni.getStorageSync("nowTable")
						})
					}
					await _this.$api.add('chatmessage',{
						uid: _this.user.id,
						fid: _this.fid,
						content: 'upload/' + obj.file,
						format: 3
					})
					_this.chatForm = {
						content: ''
					}
					uni.sendSocketMessage({
						data: 'upload/' + obj.file
					})
					_this.getChatList()
				})
			},
			async addChat() {
				let res = await this.$api.page('friend', {
					uid: this.user.id,
					fid: this.fid,
				})
				if(!res.data.list.length){
					await this.$api.add('friend',{
						uid: this.user.id,
						fid: this.fid,
						name: this.fname,
						picture: this.fpic,
						type: 2,
						tablename:  'yonghu',
					})
					await this.$api.add('friend',{
						fid: this.user.id,
						uid: this.fid,
						name: uni.getStorageSync("nickname"),
						picture: this.mpic,
						type: 2,
						tablename: uni.getStorageSync("nowTable")
					})
				}
				await this.$api.add('chatmessage',{
					uid: this.user.id,
					fid: this.fid,
					content: this.chatForm.content,
					format: 1
				})
				uni.sendSocketMessage({
					data: this.chatForm.content
				})
				this.chatForm = {
					content: ''
				}
				this.getChatList()
			},

		}
	}
</script>

<style lang="scss">
	page {
	  --animate-duration: 1s;
	  --animate-delay: 1s;
	  --animate-repeat: 1;
	}
	
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	
	.seat-list {
		display: flex;
		align-items: center;
		flex-wrap: wrap;
		background: #FFFFFF;
		margin: 20upx;
		border-radius: 20upx;
		padding: 20upx;
		font-size: 30upx;
		.seat-item {
			width: 33.33%;
			display: flex;
			align-items: center;
			flex-direction: column;
			margin-bottom: 20upx;
	
			.seat-icon {
				width: 50upx;
				height: 50upx;
				margin-bottom: 10upx;
			}
		}
	}
	
	audio {
		display: flex;
		flex-direction: column;
	}
	
	.audio .uni-audio-default {
		width: inherit !important;
	}
	

	.popup-chat{
		 background: #fff;
		 padding-bottom: 40px;
		 .chat-content {
			padding-bottom: 20px;
			width: 100%;
			margin-bottom: 10px;
			max-height: 400px;
			height: 400px;
			border: 1px solid #eeeeee;
			display: flex;
			flex-direction: column;
			
		 
			.left-content {
				float: left;
				margin-bottom: 10px;
				padding: 10px;
				max-width: 80%;
				display: flex;
				align-items: center;
				.text-content{
					background: #e5ffff;
					padding: 8px 12px;
					border-radius: 5px;
				}
				.voice-content {
					background: #e5ffff;
					padding: 8px 12px;
					border-radius: 5px;
					display: flex;
					align-items: center;
					cursor: pointer;
					.icon {
						font-size: 20px;
						margin-right: 5px;
						color: #007aff;
					}
					.voice-duration {
						font-size: 12px;
						color: #666;
						margin-left: 5px;
					}
				}
			}
					 
			.right-content {
				float: right;
				margin-bottom: 10px;
				padding: 10px;
				max-width: 80%;
				display: flex;
				align-items: center;
				.text-content{
					background: #fff;
					padding: 8px 12px;
					border-radius: 5px;
				}
				.voice-content {
					background: #fff;
					padding: 8px 12px;
					border-radius: 5px;
					display: flex;
					align-items: center;
					cursor: pointer;
					.icon {
						font-size: 20px;
						margin-right: 5px;
						color: #007aff;
					}
					.voice-duration {
						font-size: 12px;
						color: #666;
						margin-left: 5px;
					}
				}
			}
		 }
	}
	
	.voice-btn {
		width: auto;
		padding: 0 10px;
		background: #ff6b6b;
		font-size: 14px;
		border: none;
		color: #fff;
		transition: all 0.3s;
	}
	.voice-btn.recording {
		background: #ff4757;
		animation: pulse 1s infinite;
	}
	@keyframes pulse {
		0% {
			opacity: 1;
		}
		50% {
			opacity: 0.7;
		}
		100% {
			opacity: 1;
		}
	}
</style>
