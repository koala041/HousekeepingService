<template>
	<div>
		<el-form
			:style='{"padding":"30px","boxShadow":"0 0px 0px #999","borderRadius":"6px","background":"url(http://codegen.caihongy.cn/20221014/5e767dc0afa649ec9fbc71f9da086ab3.jpg)"}'
			class="add-update-preview" ref="ruleForm" :model="ruleForm" label-width="80px">
			<div class="chat-content" id="chat-content">
				<div v-bind:key="item.id" v-for="item in dataList">
					<div v-if="item.addtime" class="addtime">{{timeFormat(item.addtime)}}</div>
					<div v-if="item.ask" class="left-content">
						<div style="display: flex;align-items: flex-start;">
							<img :src="item.uimage?$base.url + item.uimage.split(',')[0]:require('@/assets/img/service.png')" style="width: 30px;height: 30px;border-radius: 50%;margin: 0 5px 0 0;" alt="">
							<el-alert v-if="item.type==1" class="text-content" :title="item.ask" :closable="false"
								type="warning"></el-alert>
							<el-image v-else-if="item.type==2" :src="$base.url + item.ask" style="width: 150px;height: 150px;" fit="cover" :preview-src-list="[$base.url + item.ask]"></el-image>
							<video v-else-if="item.type==3" :src="$base.url + item.ask" style="width: 280px;" controls></video>
							<el-button v-else-if="item.type==4" type="primary" size="mini" @click="download(item.ask)">文件预览</el-button>
						</div>
					</div>
					<div v-else class="right-content">
						<div style="display: flex;align-items: flex-start;">
							<el-alert v-if="item.type==1" class="text-content" :title="item.reply" :closable="false"
								type="success"></el-alert>
							<el-image v-else-if="item.type==2" :src="$base.url + item.reply" style="width: 150px;height: 150px;" fit="cover" :preview-src-list="[$base.url + item.reply]"></el-image>
							<video v-else-if="item.type==3" :src="$base.url + item.reply" style="width: 280px;" controls></video>
							<el-button v-else-if="item.type==4" type="primary" size="mini" @click="download(item.reply)">文件预览</el-button>
							<img :src="item.uimage?$base.url + item.uimage.split(',')[0]:require('@/assets/img/service.png')" style="width: 30px;height: 30px;border-radius: 50%;margin: 0 0 0 5px;" alt="">
						</div>
					</div>
					<div class="clear-float"></div>
				</div>
			</div>
			<div class="clear-float"></div>
			<el-form-item :style='{"margin":"0 0 20px 0"}' label="回复" prop="reply">
				<div style="width: 100%;display: flex;align-items: center;">
					<el-input @keydown.enter.native="onSubmit(null,1)" v-model="ruleForm.reply" placeholder="回复" clearable></el-input>
					<div style="position: relative;">
						<span @click="showEmoji=!showEmoji" class="icon iconfont icon-gerenzhongxin-zhihui" style="font-size: 30px;color: #666;cursor: pointer;"></span>
						<picker
							:include="['people', 'Smileys']"
							:showSearch="false"
							:showPreview="false"
							:showCategories="false"
							@select="addEmoji"
							v-if="showEmoji"
							:backgroundImageFn="((set,sheetSize)=>{
								return require('@/assets/img/32.png')
							})"
							style="position: absolute;bottom: 40px;left: -100px;"
						/>
					</div>
				</div>
			</el-form-item>
			<el-form-item :style='{"padding":"0","margin":"0 0 20px"}'>
				<div style="width: 100%;display: flex;align-items: center;">
					<img style="width: 40px;cursor: pointer;margin-right: 10px;" src="../../../assets/img/jiahao.png" @click="askShow = !askShow">
					<el-button
						:style='{"border":"1px solid #5e9808","cursor":"pointer","padding":"0","margin":"0 20px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"4px","background":"-webkit-linear-gradient(top,#96d148,#4a7f06)","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
						type="primary" @click="onSubmit(null,1)">回复</el-button>
					<el-button
						:style='{"border":"1px solid #ccc","cursor":"pointer","padding":"0","margin":"0 0","outline":"none","color":"#5b8020","borderRadius":"4px","background":"-webkit-linear-gradient(top,#fff,#ccc)","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
						@click="back()">返回</el-button>
				</div>
				
			</el-form-item>
			<el-form-item v-if="askShow" :style='{"padding":"0 0 0 50px","margin":"0"}'>
				<div style="display: flex">
					<el-upload class="upload-demo" :action="getActionUrl" :on-success="uploadSuccess" accept="image/*"
						:show-file-list="false">
						<el-button
							:style='{"border":"1px solid #5e9808","cursor":"pointer","padding":"0","margin":"0 10px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"4px","background":"-webkit-linear-gradient(top,#96d148,#4a7f06)","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
							type="primary">上传图片</el-button>
					</el-upload>
					<el-upload class="upload-demo" :action="getActionUrl" :on-success="uploadSuccess2" accept="video/*"
						:show-file-list="false">
						<el-button
							:style='{"border":"1px solid #5e9808","cursor":"pointer","padding":"0","margin":"0 10px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"4px","background":"-webkit-linear-gradient(top,#96d148,#4a7f06)","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
							type="primary">上传视频</el-button>
					</el-upload>
					<el-upload class="upload-demo" :action="getActionUrl" :on-success="uploadSuccess3"
						:show-file-list="false">
						<el-button
							:style='{"border":"1px solid #5e9808","cursor":"pointer","padding":"0","margin":"0 10px 0 0","outline":"none","color":"rgba(255, 255, 255, 1)","borderRadius":"4px","background":"-webkit-linear-gradient(top,#96d148,#4a7f06)","width":"128px","lineHeight":"40px","fontSize":"14px","height":"40px"}'
							type="primary">上传文件</el-button>
					</el-upload>
				</div>
			</el-form-item>
		</el-form>
	</div>
</template>
<script>
	import storage from "@/utils/storage";
	import { Picker } from "emoji-mart-vue";
	import {
		WebsocketMixin
	} from '@/mixins/WebsocketMixin'
	import timeMethod from '@/components/common/timeMethod'
	export default {
		mixins: [WebsocketMixin],
		data() {
			return {
				id: "",
				ruleForm: {
					reply: ''
				},
				dataList: [],
				myHeaders: {},
				askShow: false,
				showEmoji: false,
			};
		},
		props: ["parent"],
		computed: {
			imgUrl() {
				return this.$base.url
			},
			getActionUrl: function() {
				// return base.url + this.action + "?token=" + storage.get("token");
				return `/${this.$base.name}/` + 'file/upload';
			}
		},
		components:{
			Picker
		},
		mounted() {
			this.myHeaders = {
				'Token': storage.get("Token")
			}
		},
		destroyed(){
			this.websocketOnclose();
		},
		methods: {
			// 初始化
			init(id) {
				this.id = id;
				this.getList();
				this.$nextTick(()=>{
					this.initWebSocket(this.id)
				})
			},
			websocketOnmessage:function(e) {
				this.getList()
			},
			getList() {
				let params = {
					sort: 'addtime',
					order: 'asc',
					limit: 1000
				}
				this.$http({
					url: this.$api.chatbyuseridpage + this.id,
					method: "get",
					params: params
				}).then(({
					data
				}) => {
					if (data && data.code === 0) {
						this.ruleForm.userid = this.id;
						this.dataList = this.formatMessages(data.data.list);
						let div = document.getElementsByClassName('chat-content')[0]
						setTimeout(() => {
							if (div){
								div.scrollTop = div.scrollHeight
							}
						}, 100)
					} else {
						this.$message.error(data.msg);
					}
				});
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
			addEmoji(e) {
				this.ruleForm.reply += e.native;
				this.showEmoji = false
			},
			// 提交
			onSubmit(ask=null,type=1) {
				this.ruleForm.uimage = this.$storage.get('headportrait')
				this.ruleForm.uname = this.$storage.get('adminName')
				this.ruleForm.type = type
				let params = JSON.parse(JSON.stringify(this.ruleForm))
				if(params.reply==''&&ask==null){
					this.$message.error('内容不能为空')
					return false
				}
				if(ask) {
					params.reply = ask
				}
				this.$http({
					url: this.$api.chatsave,
					method: "post",
					data: params
				}).then(({
					data
				}) => {
					if (data && data.code === 0) {
						this.websocketSend(params.reply)
						this.getList();
						this.ruleForm.reply = "";
					} else {
						this.$message.error(data.msg);
					}
					this.askShow = false
				});
			},
			uploadSuccess(res) {
				if (res.code == 0) {
					this.onSubmit('upload/' + res.file,2)
					this.askShow = false
				}
			},
			uploadSuccess2(res) {
				if (res.code == 0) {
					this.onSubmit('upload/' + res.file,3)
					this.askShow = false
				}
			},
			uploadSuccess3(res) {
				if (res.code == 0) {
					this.onSubmit('upload/' + res.file,4)
					this.askShow = false
				}
			},
			// 返回
			back() {
				this.parent.showFlag = false;
				this.parent.getDataList();
				if (this.inter) {
					clearInterval(this.inter);
				}
			},
			// 预览
			download(file){
				if(!file){
					return false
				}
				window.open((location.href.split(this.$base.name).length>1 ? location.href.split(this.$base.name)[0] + this.$base.name + '/' + file :this.$base.url + file))
			},
		}
	};
</script>
<style lang="scss" scoped>
	.emoji-mart[data-v-7bc71df8] {
		font-family: -apple-system, BlinkMacSystemFont, "Helvetica Neue", sans-serif;
		display: -ms-flexbox;
		display: flex;
		-ms-flex-direction: column;
		flex-direction: column;
		height: 420px;
		color: #ffffff !important;
		border: 1px solid #d9d9d9;
		border-radius: 5px;
		background: #fff;
	}
	.chat-content {
		margin-left: 80px;
		padding-bottom: 20px;
		width: 500px;
		margin-bottom: 30px;
		max-height: 300px;
		height: 300px;
		overflow-y: scroll;
		border: 1px solid #eeeeee;
		background: #fff;

		.left-content {
			float: left;
			margin-bottom: 10px;
			padding: 10px;
		}

		.right-content {
			float: right;
			margin-bottom: 10px;
			padding: 10px;
		}
	}

	.clear-float {
		clear: both;
	}

	.detail-form-content {
		background-color: transparent;
	}

	.el-input {
		width: auto;
	}

	.add-update-preview .el-form-item ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		text-shadow: 0 1px 10px #fff;
		color: #699620;
		width: 80px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}

	.add-update-preview .el-form-item ::v-deep .el-form-item__content {
		margin-left: 80px;
	}

	.add-update-preview .el-input ::v-deep .el-input__inner {
		border: 0;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: 0 0 1px #4b681d;
		outline: none;
		color: #00a9aa;
		width: 400px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview .el-select ::v-deep .el-input__inner {
		border: 0;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: 0 0 1px #4b681d;
		outline: none;
		color: #00a9aa;
		width: 200px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview .el-date-editor ::v-deep .el-input__inner {
		border: 0;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: 0 0 1px #4b681d;
		outline: none;
		color: #00a9aa;
		width: 200px;
		font-size: 14px;
		height: 40px;
	}

	.add-update-preview ::v-deep .el-upload--picture-card {
		background: transparent;
		border: 0;
		border-radius: 0;
		width: auto;
		height: auto;
		line-height: initial;
		vertical-align: middle;
	}

	.add-update-preview ::v-deep .upload .upload-img {
		border: 1px dashed #fff;
		cursor: pointer;
		border-radius: 6px;
		color: #fff;
		width: 200px;
		font-size: 32px;
		line-height: 100px;
		text-align: center;
		height: 100px;
	}

	.add-update-preview ::v-deep .el-upload-list .el-upload-list__item {
		border: 1px dashed #fff;
		cursor: pointer;
		border-radius: 6px;
		color: #fff;
		width: 200px;
		font-size: 32px;
		line-height: 100px;
		text-align: center;
		height: 100px;
	}

	.add-update-preview ::v-deep .el-upload .el-icon-plus {
		border: 1px dashed #fff;
		cursor: pointer;
		border-radius: 6px;
		color: #fff;
		width: 200px;
		font-size: 32px;
		line-height: 100px;
		text-align: center;
		height: 100px;
	}

	.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
		border: 0;
		border-radius: 4px;
		padding: 12px;
		box-shadow: 0 0 1px #4b681d;
		outline: none;
		color: #00a9aa;
		width: 400px;
		font-size: 14px;
		height: auto;
	}
	.addtime {
		width: 100%;
		text-align: center;
		font-size: 12px;
		color: #999;
	}
</style>