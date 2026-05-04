<template>
	<view class="content">
		<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}'>
			<view v-if="user&&user.id" :style='{"minHeight":"400rpx","padding":"40rpx 32rpx 20rpx 24rpx","margin":"0 0 20rpx 0","borderRadius":"0","background":"linear-gradient(135deg, #fff1f5 0%, #ffe1eb 55%, #fff9f6 100%)","display":"flex","width":"100%","position":"relative","height":"auto"}' @tap="onPageTap('../user-info/user-info')" class="header" v-bind:class="{'status':isH5Plus}">
				<view :style='{"alignContent":"center","flexWrap":"wrap","flex":"1","display":"flex","height":"100%"}' v-if="tableName=='yonghu'" class="userinfo">
					<image :style='{"width":"128rpx","padding":"0","margin":"0 20rpx 0 0","borderRadius":"128rpx","height":"128rpx"}' :src="user.touxiang?baseUrl+user.touxiang:require('../../static/gen/upload.png')"></image>
					<view :style='{"alignContent":"center","margin":"20rpx 0 0 0","alignItems":"center","flexWrap":"wrap","flex":"1","display":"flex","width":"100%"}' class="info">
						<view :style='{"width":"100%","padding":"0","lineHeight":"48rpx","fontSize":"24rpx","color":"#333"}'>{{user.yonghuzhanghao}}<text v-if="user.vip&& user.vip=='是'">(VIP)</text></view>
					</view>
					
					<view :style='{"border":"2rpx solid #eee","width":"100%","padding":"20rpx","margin":"20rpx 0 0 0","borderRadius":"12rpx","background":"#ffffff50"}' class="info">
						<view :style='{"width":"100%","lineHeight":"60rpx","fontSize":"24rpx"}'>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>手机号：</span>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>{{user.mobile}}</span>
						</view>
						<view :style='{"width":"100%","lineHeight":"60rpx","fontSize":"24rpx"}' v-if="user.jf||user.jf==0">
							<span :style='{"color":"#333"}'>积分：</span>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>{{user.jf}}</span>
						</view>
					</view>
					<view :style='{"width":"100%","margin":"20rpx 0 20rpx 0","justifyContent":"center","display":"flex"}'>
					  <view :style='{"margin":"0 60rpx 0 60rpx","display":"flex"}'>
					    <text class="icon iconfont icon-zan10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#333"}'></text>
					    <text :style='{"color":"#333","lineHeight":"1.5","fontSize":"28rpx"}'>点赞 {{user.thumbsnum}}</text>
					  </view>
					  <view :style='{"margin":"0 60rpx 0 60rpx","display":"flex"}'>
					    <text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#333"}'></text>
					    <text :style='{"color":"#333","lineHeight":"1.5","fontSize":"28rpx"}'>收藏 {{user.storenum}}</text>
					  </view>
					</view>
				</view>
				<view :style='{"alignContent":"center","flexWrap":"wrap","flex":"1","display":"flex","height":"100%"}' v-if="tableName=='fuwurenyuan'" class="userinfo">
					<image :style='{"width":"128rpx","padding":"0","margin":"0 20rpx 0 0","borderRadius":"128rpx","height":"128rpx"}' :src="user.touxiang?baseUrl+user.touxiang:require('../../static/gen/upload.png')"></image>
					<view :style='{"alignContent":"center","margin":"20rpx 0 0 0","alignItems":"center","flexWrap":"wrap","flex":"1","display":"flex","width":"100%"}' class="info">
						<view :style='{"width":"100%","padding":"0","lineHeight":"48rpx","fontSize":"24rpx","color":"#333"}'>{{user.yuangongzhanghao}}<text v-if="user.vip&& user.vip=='是'">(VIP)</text></view>
					</view>
					
					<view :style='{"border":"2rpx solid #eee","width":"100%","padding":"20rpx","margin":"20rpx 0 0 0","borderRadius":"12rpx","background":"#ffffff50"}' class="info">
						<view :style='{"width":"100%","lineHeight":"60rpx","fontSize":"24rpx"}'>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>手机号：</span>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>{{user.mobile}}</span>
						</view>
						<view :style='{"width":"100%","lineHeight":"60rpx","fontSize":"24rpx"}' v-if="user.jf||user.jf==0">
							<span :style='{"color":"#333"}'>积分：</span>
							<span :style='{"color":"#333","fontSize":"24rpx"}'>{{user.jf}}</span>
						</view>
					</view>
					<view :style='{"width":"100%","margin":"20rpx 0 20rpx 0","justifyContent":"center","display":"flex"}'>
					  <view :style='{"margin":"0 60rpx 0 60rpx","display":"flex"}'>
					    <text class="icon iconfont icon-zan10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#333"}'></text>
					    <text :style='{"color":"#333","lineHeight":"1.5","fontSize":"28rpx"}'>点赞 {{user.thumbsnum}}</text>
					  </view>
					  <view :style='{"margin":"0 60rpx 0 60rpx","display":"flex"}'>
					    <text class="icon iconfont icon-shoucang10" :style='{"margin":"0 4rpx 0 0","lineHeight":"1.5","fontSize":"28rpx","color":"#333"}'></text>
					    <text :style='{"color":"#333","lineHeight":"1.5","fontSize":"28rpx"}'>收藏 {{user.storenum}}</text>
					  </view>
					</view>
				</view>
				<view :style='{"alignItems":"center","top":"20rpx","flexDirection":"column","display":"flex","width":"84rpx","position":"absolute","right":"20rpx","justifyContent":"center","height":"auto"}' class="setting">
					<text class="icon iconfont icon-qita6" :style='{"border":"0","width":"64rpx","lineHeight":"64rpx","fontSize":"64rpx","color":"#333","borderRadius":"0"}'></text>
					<text :style='{"color":"#333","lineHeight":"2","fontSize":"24rpx"}'>设置</text>
				</view>
			</view>
			
			<view v-else :style='{"minHeight":"400rpx","padding":"40rpx 32rpx 20rpx 24rpx","margin":"0 0 20rpx 0","borderRadius":"0","background":"linear-gradient(135deg, #fff1f5 0%, #ffe1eb 55%, #fff9f6 100%)","display":"flex","width":"100%","position":"relative","height":"auto"}' @tap="loginClick" class="header" v-bind:class="{'status':isH5Plus}">
				<view :style='{"alignItems":"center","color":"#666","display":"flex","width":"100%","fontSize":"36rpx","justifyContent":"center","height":"100%"}'>登录/注册</view>
			</view>
			
			<view :style='{"width":"100%","background":"#fff","height":"auto"}' class="list">
				
				<view :style='{"width":"100%","height":"auto"}'>
					<view :style='{"padding":"0 20rpx 20rpx 20rpx","margin":"40rpx 0 20rpx 0","background":"none","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
						<view :style='{"padding":"0 0 0 10rpx","borderColor":"#ff6f9f","color":"#ff6f9f","borderWidth":"0 0 0 4rpx","fontSize":"36rpx","lineHeight":"1","borderStyle":"solid"}'>我的服务</view>
					</view>
					<view class="me-menu-view">
						<block v-for="item in menuList" v-bind:key="item.roleName">
							<block v-if="role==item.roleName" v-bind:key="menu.menu" v-for="(menu,index) in item.backMenu">
								<block v-bind:key="child.tableName" v-for=" (child,sort) in menu.child">
									<view class="me-menu-item" v-if="hasBack(child.tableName)" @tap="onPageTap('../'+child.tableName+'/list?userid='+user.id+'&menuJump='+child.menuJump)" hover-class="hover">
										<view class="me-menu-icon" :class="child.appFrontIcon"></view>
										<view class="text">{{child.menu}}</view>
										<view class="icon iconfont icon-jinru"></view>
									</view>
								</block>
							</block>
						</block>


						<view v-if="user&&user.id" @tap="onPageTap('../chat/chat')" class="me-menu-item" hover-class="hover">
							<view class="cuIcon-service me-menu-icon"></view>
							<view class="text">在线客服</view>
							<view class="icon iconfont icon-jinru"></view>
						</view>
						<view v-if="changeHasChat()" @tap="getRecordList(1)" class="me-menu-item" hover-class="hover" style="position: relative;">
							<view class="cuIcon-news me-menu-icon"></view>
							<view class="text">聊天记录</view>
							<view class="icon iconfont icon-jinru"></view>
							<view v-if="redNum>0" style="background: red;color: #fff;border-radius: 50%;font-size: 10px;position: absolute; top: 0;right: 0;width: 16px;height: 16px;line-height: 16px;text-align: center;">{{redNum}}</view>
						</view>
						<view v-if="role=='服务人员' && user&&user.id" @tap="openAcceptOrders" class="me-menu-item accept-order-entry" hover-class="hover" style="position: relative;">
							<view class="cuIcon-deliver me-menu-icon"></view>
							<view class="text">接单服务</view>
							<view class="icon iconfont icon-jinru"></view>
							<view v-if="newOrderCount>0" class="order-badge">{{newOrderCount}}</view>
						</view>
						<view v-if="user&&user.id" @tap="passwordShow()" class="me-menu-item" hover-class="hover">
							<view class="cuIcon-lock me-menu-icon"></view>
							<view class="text">修改密码</view>
							<view class="icon iconfont icon-jinru"></view>
						</view>
					</view>
				</view>
			</view>
			
			<!-- 推荐 -->
			<view class="recommend" :style='{"width":"100%","padding":"0 24rpx 0 24rpx","margin":"20rpx 0 0 0","background":"#ffffff","height":"auto"}' v-if="recommendList.length">
				<view :style='{"padding":"0 0 0 10rpx","borderColor":"#ff6f9f","margin":"0 0 20rpx 0","color":"#ff6f9f","borderWidth":"0 0 0 4rpx","fontSize":"36rpx","lineHeight":"1","borderStyle":"solid"}'>为你推荐</view>
				<view :style='{"padding":"0","flexWrap":"wrap","background":"#fff","display":"flex","width":"100%","justifyContent":"space-between","height":"auto"}'>
					<view v-for="(item,index) in recommendList" :key="index" :style='{"width":"100%","margin":"20rpx 0 0 0","background":"#fff","height":"auto"}' @click="recommendDetail(item.id)">
						<image v-if="preHttp(item[recommendPicture])" :style='{"width":"30%","objectFit":"cover","borderRadius":"12rpx","float":"left","display":"block","height":"200rpx"}' :src="item[recommendPicture]"></image>
						<image v-else-if="item[recommendPicture]" :style='{"width":"30%","objectFit":"cover","borderRadius":"12rpx","float":"left","display":"block","height":"200rpx"}' :src="baseUrl + item[recommendPicture].split(',')[0]"></image>
						<image v-else :style='{"width":"30%","objectFit":"cover","borderRadius":"12rpx","float":"left","display":"block","height":"200rpx"}' :src="getDefaultImage(item.id)"></image>
						<view :style='{"padding":"0","margin":"0","overflow":"hidden","whiteSpace":"nowrap","color":"#333","width":"66%","lineHeight":"48rpx","fontSize":"28rpx","textOverflow":"ellipsis","float":"right","fontWeight":"500"}'>{{item[recommendTitle]}}</view>
						<view :style='{"padding":"0","color":"#999","textAlign":"right","width":"66%","lineHeight":"24rpx","fontSize":"24rpx","float":"right"}'>{{item.addtime}}</view>
					</view>
				</view>
			</view>
			<view style="width: 100%;height: 80px"></view>
		</view>
		<uni-popup class="popup-content" ref="recordPopup" type="bottom">
			<view class="z-box">
				<view class="section-content" v-for="item in recordList" :key="item.id" @click="chatClick(item)">
					<view style="display: flex;align-items: center;">
						<img :src="item.picture?baseUrl + item.picture:''" style="width: 60px;height: 60px;border-radius: 50%;object-fit: cover;"
							alt="">
						<view style="margin: 0 0 0 10px;display: flex;flex-direction: column;align-items: flex-start;">
							<view :style='{"fontSize":"14px","color":"#000","flex":"1","fontWeight":"bold"}'
								class="item-style">{{item.name}}</view>
							<view style="display: flex;align-items: center;" :style='{"color":"#666","flex":"1","fontSize":"14px","lineHeight":"1.5"}'
								class="item-style">
								<view v-if="item.notreadnum"
									style="padding: 0 5px;height: 16px;border-radius: 50%;text-align: center;line-height: 16px;font-size: 12px;background: #f00;color:#fff;width: auto;display: flex; align-items: center">
									{{item.notreadnum}}
								</view>
								{{item.content.split('/').length&&item.content.split('/')[0]=='upload'?'[多媒体]':item.content}}
							</view>
						</view>
					</view>
					<view>
						<button @click.stop="delChat(item)" style="width: auto;padding: 0 10px;line-height: 20px;height: 20px;font-size: 14px;background: #f93636;margin: 4px 0;color: #fff;">删除</button>
					</view>
				</view> 
				<view v-if="!recordList.length" style="padding: 30px 0;width: 100%;text-align: center;font-size: 14px;color: #666;">
					暂无聊天记录				</view>
			</view>
		</uni-popup>
		<uni-popup class="popup-content" ref="chatPopup" type="bottom" :maskClick="true" @change="clearChat">
			<view class="popup-chat">
				<scroll-view :scrollTop="scrollTop" scroll-y="true" style="background: #eee;">
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
								<img :src="mypic?baseUrl + mypic:''" alt=""
									style="width: 30px;border-radius: 50%;height: 30px;margin: 0 0 0 10px;object-fit: cover;" />
							</view>
							<view v-else class="left-content">
								<img :src="nowfpic?baseUrl + nowfpic:''" alt=""
									style="width: 30px;border-radius: 50%;height: 30px;margin: 0 10px 0 0;object-fit: cover;" />
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
				<view style="padding: 0 0 20px;">
					<input v-model="chatForm.content" placeholder="请输入内容" confirm-type="send" @confirm="addChat"
						style="width: calc(100% - 20px);font-size: 14px;background: #eee;margin: 0 10px 10px;padding: 0 10px;">
					</input>
					<view style="display: flex;justify-content: center;">
						<button @click="addChat" style="width: auto;padding: 0 10px;background: #ff6f9f;font-size: 13px;border: none;color: #fff;">发送</button>
						<button class="voice-btn" :class="isRecording ? 'recording' : ''" @click="toggleRecord">
							{{isRecording ? '停止录音(' + recordDuration + 's)' : '开始录音'}}
						</button>
						<button @click="uploadSuccess" style="width: auto;padding: 0 10px;background: #ff9ab9;font-size: 13px;border: none;color: #fff;">上传图片</button>
						<button @click="uploadSuccess2" style="width: auto;padding: 0 10px;background: #ff9ab9;font-size: 13px;border: none;color: #fff;">上传视频</button>
						<button @click="closeChat" style="width: auto;padding: 0 10px;background: #c774b1;font-size: 13px;border: none;color: #fff;">返回</button>
					</view>
					
				</view>
			</view>
		</uni-popup>
		<uni-popup class="popup-content" ref="passwordPopup" type="bottom">
			<view class="passwordForm">
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.mima" placeholder="原密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima" placeholder="新密码" />
				</view>
				<view class="passwordInput">
					<input type="password" v-model="passwordForm.newmima1" placeholder="确认密码" />
				</view>
				<view class="passwordBtnView">
					<button class="passwordBtn" @click="updatePassword">更新</button>
				</view>
			</view>
		</uni-popup>
	</view>
</template>
<script>
	import timeMethod from '@/utils/timeMethod'
	import menu from '@/utils/menu'
	export default {
		data() {
			return {
				isH5Plus: true,
				user: {},
				tableName:'',
				role: '',
				menuList: [],
				iconArr: [
					'cuIcon-same',
					'cuIcon-deliver',
					'cuIcon-evaluate',
					'cuIcon-shop',
					'cuIcon-ticket',
					'cuIcon-cascades',
					'cuIcon-discover',
					'cuIcon-question',
					'cuIcon-pic',
					'cuIcon-filter',
					'cuIcon-footprint',
					'cuIcon-pulldown',
					'cuIcon-pullup',
					'cuIcon-moreandroid',
					'cuIcon-refund',
					'cuIcon-qrcode',
					'cuIcon-remind',
					'cuIcon-profile',
					'cuIcon-home',
					'cuIcon-message',
					'cuIcon-link',
					'cuIcon-lock',
					'cuIcon-unlock',
					'cuIcon-vip',
					'cuIcon-weibo',
					'cuIcon-activity',
					'cuIcon-friendadd',
					'cuIcon-friendfamous',
					'cuIcon-friend',
					'cuIcon-goods',
					'cuIcon-selection'
				],
				recommendList: [],
				recommendTable: '',
				recommendTitle: '',
				recommendPicture: '',
				// 聊天
				redNum: 0,
				newOrderCount: 0,
				lastOrderCount: 0,
				recordList: [],
				nowfid: 0,
				nowfpic: '',
				nowname: '',
				nowtablename: '',
				chatList: [],
				scrollTop: 0,
				chatForm: {
					content: ''
				},
				hasChatList: [
					'yonghu',
					'fuwurenyuan',
				],
				friendList: [],
				mypic:'',
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
				passwordForm: {
					mima: '',
					newmima: '',
					newmima1: '',
				},
				passwordForm: {
					mima: '',
					newmima: '',
					newmima1: '',
				},
			};
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
		},
		async onLoad(){
			let menus = menu.list();
			this.menuList = menus;
		},
		async onShow(){
            uni.removeStorageSync("useridTag");
			this.role = uni.getStorageSync("appRole");
			await this.getSession()
			this.getThumbsup()
			this.getStoreup()
			this.tableName = uni.getStorageSync("nowTable");
			let menus = menu.list();
			this.menuList = menus;
			this.getRecommendList()
			this.mypic = uni.getStorageSync('frontHeadportrait')
			if(this.user&&this.user.id) {
				this.getRecordList2()
			}
			if(this.role=='服务人员' && this.user&&this.user.id) {
				this.checkNewOrders()
			}
			this.$forceUpdate()
		},
		methods: {
			hasBack(tableName) {
				if(tableName == 'hasBoard') {
					return false
				}
				if(tableName.indexOf('chapter')!=-1) {
					return false
				}
				return true
			},
			async getSession(){
				let table = uni.getStorageSync("nowTable");
				let res = await this.$api.session(table);
				this.user = res.data;
				this.$forceUpdate()
			},
			async getThumbsup(){
				if(!this.user||!this.user.id){
					return false
				}
				let res = await this.$api.page('storeup',{userid: this.user.id,type: 21})
				this.user.thumbsnum = Number(res.data.total)
				this.$forceUpdate()
			},
			async getStoreup(){
				if(!this.user||!this.user.id){
					return false
				}
				let res = await this.$api.page('storeup',{userid: this.user.id,type: 1})
				this.user.storenum = Number(res.data.total)
				this.$forceUpdate()
			},
			loginClick(){
				uni.navigateTo({
					url: '../login/login'
				});
			},
			onPageTap(url) {
                uni.setStorageSync("useridTag",1);
				uni.navigateTo({
					url: url,
					fail: function() {
						uni.switchTab({
							url: url
						});
					}
				});
			},
			openAcceptOrders() {
				uni.setStorageSync("useridTag", 1);
				uni.navigateTo({
					url: '../fuwudingdan/list?userid=' + this.user.id + '&menuJump=列表&acceptMode=1'
				});
			},
			async checkNewOrders() {
				let res = await this.$api.page('fuwudingdan', {
					page: 1,
					limit: 100,
					fuwuzhuangtai: '待服务'
				})
				const count = res && res.data ? Number(res.data.total || 0) : 0
				this.newOrderCount = count
				if(count > 0 && count !== this.lastOrderCount) {
					this.lastOrderCount = count
					uni.vibrateShort({ fail: () => {} })
					// #ifdef APP-PLUS
					if(typeof plus !== 'undefined' && plus.speech) plus.speech.speak('您有新的家政订单待接单')
					// #endif
					uni.showToast({
						title: '您有新的家政订单待接单',
						icon: 'none',
						duration: 2200
					})
				}
			},
			hasTranslate(){
				for(let x in this.menuList){
					if(this.menuList[x].roleName == this.role){
						for(let i in this.menuList[x].backMenu){
							if(this.menuList[x].backMenu[i].child.length) {
								for(let j in this.menuList[x].backMenu[i].child) {
									if(this.menuList[x].backMenu[i].child[j].tableName=='hasTranslate'){
										return true
									}
								}
							}
						}
					}
				}
				return false
			},
			
			preHttp(str) {
			    return str && str.substr(0,4)=='http';
			},
			getDefaultImage(id){
				const random = (id * 9973) % 22 + 1
				return `/static/default/default${random}.jpg`
			},
			async getRecommendList(){
				let params = {
					page: 1,
					limit: 4,
				}
				let res;
				if(uni.getStorageSync("appUserid")) {
					res = await this.$api.recommend2('fuwuxinxi', params);
				} else {
					res = await this.$api.recommend('fuwuxinxi', params);
				}
				this.recommendList = res.data.list
				this.recommendTable = 'fuwuxinxi'
				this.recommendTitle = 'xiangmumingcheng'
				this.recommendPicture = 'xiangmutupian'
				this.$forceUpdate()
			},
			recommendDetail(id) {
				uni.navigateTo({
					url: `../${this.recommendTable}/detail?id=${id}`,
					fail: function() {
						uni.switchTab({
							url: `../${this.recommendTable}/detail?id=${id}`
						});
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
			changeHasChat(){
				let table = uni.getStorageSync("nowTable")
				for(let x in this.hasChatList){
					if(this.hasChatList[x]==table){
						return true
					}
				}
				return false
			},
			async getRecordList(type=1) {
				let res = await this.$api.page2('friend',{
					uid: this.user.id,
					type: 2
				})
				this.recordList = res.data.list
				if(type==1){
					this.$refs.recordPopup.open()
				}
				
			},
			async getRecordList2() {
				let res = await this.$api.page2('friend',{
					uid: this.user.id,
					type: 2
				})
				let num = 0
				for(let x in res.data.list) {
					num += Number(res.data.list[x].notreadnum)
				}
				this.redNum = num
				
			},
			async chatClick(row) {
				if (row.type == 0) {
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
				this.nowfid = row.fid
				this.nowfpic = row.picture
				this.nowname = row.name
				this.nowtablename = row.tablename
				await this.getChatList()
				uni.connectSocket({
					url: 'ws' + this.$base.url.split('http')[1] + '/ws?user_id=' + uni.getStorageSync("appUserid") + '&to_id=' + this.nowfid
				})
				uni.onSocketMessage((res)=> {
					this.getChatList()
				})
				this.$refs.chatPopup.open()
				this.$forceUpdate()
			},
			async delChat(item) {
				// #ifdef MP-WEIXIN
				item = item.$orig || item
				// #endif
				let that = this
				uni.showModal({
					title: '提示',
					content: '是否删除此聊天记录？',
					success: async function(obj) {
						if (obj.confirm) {
							let arr = []
							let brr = []
							let res = await that.$api.list('chatmessage',{page: 1,limit: 10000,uid: Number(uni.getStorageSync('appUserid')),fid: item.fid})
							if(res.data.list.length){
								for(let x in res.data.list){
									arr.push(res.data.list[x].id)
								}
							}
							res = await that.$api.list('chatmessage',{page: 1,limit: 10000,uid: item.fid,fid: Number(uni.getStorageSync('appUserid'))})
							if(res.data.list.length){
								for(let x in res.data.list){
									arr.push(res.data.list[x].id)
								}
							}
							if(arr.length){
								await that.$api.del('chatmessage',JSON.stringify(arr))
							}
							res = await that.$api.list('friend',{page: 1,limit: 10000,uid: Number(uni.getStorageSync('appUserid')),fid: item.fid})
							if(res.data.list.length){
								for(let x in res.data.list){
									brr.push(res.data.list[x].id)
								}
							}
							res = await that.$api.list('friend',{page: 1,limit: 10000,uid: item.fid,fid: Number(uni.getStorageSync('appUserid'))})
							if(res.data.list.length){
								for(let x in res.data.list){
									brr.push(res.data.list[x].id)
								}
							}
							if(brr.length){
								await that.$api.del('friend',JSON.stringify(brr))
							}
							that.$utils.msg('操作成功');
							that.getRecordList(0)
						}
					}
				})
			},
			async getChatList() {
				this.scrollTop = 0
				let res = await this.$api.mlist('chatmessage',{
					page: 1,
					limit: 1000,
					uid: this.user.id,
					fid: this.nowfid
				})
				this.chatList = this.formatMessages(res.data.list)
				setTimeout(() => {
					this.scrollTop = 1000000
				}, 100)
			},
			preViewImg(img){
				uni.previewImage({
					urls: [img],
					current:img
				})
			},
			clearChat(e) {
				if(!e.show){
					uni.closeSocket()
				}
				// 页面卸载时清理资源
				if (this.recordTimer) {
					clearInterval(this.recordTimer);
				}
				// #ifdef H5
				// H5环境清理录音资源
				if (this.mediaRecorder && this.mediaRecorder.state !== 'inactive') {
					this.mediaRecorder.stop();
				}
				if (this.audioStream) {
					this.audioStream.getTracks().forEach(track => track.stop());
					this.audioStream = null;
				}
				this.audioChunks = [];
				// #endif
				// #ifndef H5
				// 原生环境清理录音资源
				if (this.recorderManager) {
					this.recorderManager.stop();
				}
				// #endif
				if (this.innerAudioContext) {
					this.innerAudioContext.stop();
					this.innerAudioContext.destroy();
				}
				this.isRecording = false;
				this.recordDuration = 0;
				this.getRecordList(0)
				this.getRecordList2()
			},
			closeChat(){
				this.$refs.chatPopup.close()
			},
			
			// 语音聊天功能 - 切换录音状态
			toggleRecord() {
				// 检查是否已选择聊天对象
				if (!this.nowfid || !this.user || !this.user.id) {
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
						// 发送语音消息
						await this.$api.add('chatmessage', {
							uid: that.user.id,
							fid: that.nowfid,
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
							// 发送语音消息
							await this.$api.add('chatmessage', {
								uid: that.user.id,
								fid: that.nowfid,
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
			async uploadSuccess() {
				let _this = this;
				await this.$api.upload(async function(obj) {
					let res = await _this.$api.page('friend', {
						uid: _this.user.id,
						fid: _this.nowfid,
						type: 2
					})
					if(!res.data.list.length){
						await _this.$api.add('friend',{
							uid: _this.user.id,
							fid: _this.nowfid,
							name: _this.nowname,
							picture: _this.nowfpic,
							type: 2,
							tablename: _this.nowtablename
						})
						await _this.$api.add('friend',{
							fid: _this.user.id,
							uid: _this.nowfid,
							name: uni.getStorageSync("nickname"),
							picture: _this.mypic,
							type: 2,
							tablename: uni.getStorageSync("nowTable")
						})
					}
					await _this.$api.add('chatmessage',{
						uid: _this.user.id,
						fid: _this.nowfid,
						content: 'upload/' + obj.file,
						format: 2
					})
					uni.sendSocketMessage({
						data: 'upload/' + obj.file
					})
					_this.chatForm = {
						content: ''
					}
					_this.getChatList()
				})
			},
			async uploadSuccess2() {
				let _this = this;
				await this.$api.uploadMedia(async function(obj) {
					let res = await _this.$api.page('friend', {
						uid: _this.user.id,
						fid: _this.nowfid,
						type: 2
					})
					if(!res.data.list.length){
						await _this.$api.add('friend',{
							uid: _this.user.id,
							fid: _this.nowfid,
							name: _this.nowname,
							picture: _this.nowfpic,
							type: 2,
							tablename: _this.nowtablename
						})
						await _this.$api.add('friend',{
							fid: _this.user.id,
							uid: _this.nowfid,
							name: uni.getStorageSync("nickname"),
							picture: _this.mypic,
							type: 2,
							tablename: uni.getStorageSync("nowTable")
						})
					}
					await _this.$api.add('chatmessage',{
						uid: _this.user.id,
						fid: _this.nowfid,
						content: 'upload/' + obj.file,
						format: 3
					})
					uni.sendSocketMessage({
						data: 'upload/' + obj.file
					})
					_this.chatForm = {
						content: ''
					}
					_this.getChatList()
				})
			},
			async addChat() {
				let res = await this.$api.page('friend', {
					uid: this.user.id,
					fid: this.nowfid,
					type: 2
				})
				if(!res.data.list.length){
					await this.$api.add('friend',{
						uid: this.user.id,
						fid: this.nowfid,
						name: this.nowname,
						picture: this.nowfpic,
						type: 2,
						tablename: this.nowtablename
					})
					await this.$api.add('friend',{
						fid: this.user.id,
						uid: this.nowfid,
						name: uni.getStorageSync("nickname"),
						picture: this.mypic,
						type: 2,
						tablename: uni.getStorageSync("nowTable")
					})
				}
				await this.$api.add('chatmessage',{
					uid: this.user.id,
					fid: this.nowfid,
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
			passwordShow() {
				this.passwordForm = {
					mima: '',
					newmima: '',
					newmima1: '',
				}
				this.$forceUpdate()
				this.$refs.passwordPopup.open()
			},
			async updatePassword() {
				if (this.passwordForm.mima == ''){
					this.$utils.msg('原密码不能为空')
					return false
				}
				if (this.passwordForm.newmima == ''){
					this.$utils.msg('新密码不能为空')
					return false
				}
				if (this.passwordForm.newmima1 == ''){
					this.$utils.msg('确认密码不能为空')
					return false
				}
				let password = ''
				if (this.user.mima) {
					password = this.user.mima;
				} else if (this.user.password) {
					password = this.user.password;
				}
				if (this.tableName == 'yonghu') {
				}
				if (this.tableName == 'fuwurenyuan') {
				}
				let newpassword = this.passwordForm.mima
				if(password != newpassword){
					this.$utils.msg('原密码不正确')
					return false
				}
				if (this.passwordForm.newmima != this.passwordForm.newmima1){
					this.$utils.msg('两次密码不一致')
					return false
				}
				if (this.passwordForm.mima == this.passwordForm.newmima){
					this.$utils.msg('新密码与原密码相同！')
					return false
				}
				this.user.password = this.passwordForm.newmima
				this.user.mima = this.passwordForm.newmima
				await this.$api.update(this.tableName,this.user)
				this.$utils.msg('修改密码成功,下次登录系统生效')
				this.$refs.passwordPopup.close()
				this.getSession()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		height: calc(100vh - 94px);
		box-sizing: border-box;
	}
	.z-box{
		background: #fff;
		width: 100%;
		padding: 10px 0 60px;
		max-height: 60vh;
		overflow-y: scroll;
		
		.section-content {
			cursor: pointer;
			padding: 10px;
			box-shadow: 0px 4px 10px 0px rgba(0, 0, 0, 0.3);
			margin: 0 auto 20px;
			color: #333;
			background: #fff;
			display: flex;
			width: calc(100% - 20px);
			border-color: #efefef;
			border-width: 0;
			align-items: center;
			border-style: solid;
			position: relative;
			justify-content: space-between;
		}
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
	.passwordForm {
		width: 100%;
		padding: 10px 20px 60px;
		background: #fff;
	
		.passwordInput {
			background: #eee;
			margin: 0 0 10px;
			padding: 0 10px;
	
			input {
				height: 36px;
				line-height: 36px;
				font-size: 14px;
			}
		}
	
		.passwordBtnView {
			width: 100%;
			display: flex;
			align-items: center;
			justify-content: center;
			padding: 10px 0;
	
			.passwordBtn {
				padding: 0 20px;
				width: auto;
				height: 20px;
				line-height: 20px;
				font-size: 14px;
				color: #fff;
				background: #007aff;
				border: none;
				border-radius: 5px;
	
			}
		}
	}
	.me-menu-view {
		padding: 0 24rpx 0 24rpx;
		display: flex;
		width: 100%;
		justify-content: space-between;
		flex-wrap: wrap;
		height: auto;
		.me-menu-item {
			border-radius: 20rpx;
			padding: 10rpx;
			margin: 0 0 30rpx 0;
			flex-direction: column;
			display: flex;
			width: 48%;
			border-color: #ff6f9f30;
			border-width: 2rpx;
			justify-content: center;
			align-items: center;
			border-style: solid;
			height: auto;
			.me-menu-icon {
				color: #ff6f9f;
				font-size: 84rpx;
				line-height: 1.2;
			}
			.text {
				padding: 0 20rpx 0 20rpx;
				color: #333333;
				flex: 1;
				width: 100%;
				font-size: 28rpx;
				line-height: 1.2;
				text-align: center;
			}
			.icon {
				color: #ffaa33;
				display: none;
				width: 28rpx;
				font-size: 32rpx;
				line-height: 28rpx;
			}
		}
		.accept-order-entry {
			background: linear-gradient(135deg, #fff1f5, #ffffff);
			border-color: #ff8fb230;
			box-shadow: 0 12rpx 28rpx rgba(255, 111, 159, .14);
		}
		.order-badge {
			position: absolute;
			top: 10rpx;
			right: 14rpx;
			min-width: 34rpx;
			height: 34rpx;
			padding: 0 8rpx;
			border-radius: 999rpx;
			background: #ff4f87;
			color: #fff;
			font-size: 22rpx;
			line-height: 34rpx;
			text-align: center;
		}
	}
</style>
