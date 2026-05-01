<template>
	<view class="content">
		<view class="login-box" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<view :style='{"padding":"0 40rpx","flexWrap":"wrap","display":"flex","width":"100%","position":"relative","justifyContent":"center","height":"auto"}'>
				<image :style='{"width":"180rpx","margin":"0 auto 48rpx auto","borderRadius":"160rpx","display":"none","height":"180rpx"}' :src="indexLogoUrl?($base.url + indexLogoUrl):'/static/service.png'" mode="aspectFill"></image>
				<view v-if="loginType==1" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">账号：</view>
					<input v-model="username" :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="text" class="uni-input" name="" placeholder="请输入账号" />
				</view>
				<view v-if="loginType==1" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">密码：</view>
					<input v-model="password" password :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="password" class="uni-input" name="" placeholder="请输入密码" />
				</view>
				<view v-if="loginType==4" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' class="uni-form-item uni-column">
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">手机号：</view>
					<input v-model="phone" :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="text" class="uni-input" name="" placeholder="请输入手机号" />
				</view>
				<view v-if="loginType==4" class="code" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}'>
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">验证码：</view>
					<input :style='{"padding":"0 24rpx","margin":"0","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","width":"100%","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="text" v-model="phonecode" placeholder="请输入验证码" />
					<view v-if="isEndFlag" class="getCodeBt" :style='{"padding":"0px","margin":"0 0 0 10rpx","borderColor":"#ff6f9f50","alignItems":"center","display":"flex","justifyContent":"center","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"160rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' @click="phoneClick()">
						发送验证码
					</view>
					<view v-if="!isEndFlag" class="getCodeBt" :style='{"padding":"0px","margin":"0 0 0 10rpx","borderColor":"#ff6f9f50","alignItems":"center","display":"flex","justifyContent":"center","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"160rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}'>
						{{emailText}}
					</view>
				</view>
				<view v-if="phoneroles.length>2&&loginType==4" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 40rpx 0","alignItems":"center","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}'>
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">用户类型：</view>
					<picker @change="phonerolesChange" :value="phonerolesIndex" :range="phoneroles" range-key="roleName" :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ff6f9f50","color":"#666","borderRadius":"40rpx","flex":"1","borderWidth":"2rpx","background":"#ffffff90","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}'>
						<view class="uni-picker-type">{{phoneroles[phonerolesIndex].roleName}}</view>
					</picker>
				</view>
				<view v-if="roleNum>1&&loginType<=2" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 40rpx 0","alignItems":"center","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}'>
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">用户类型：</view>
					<picker @change="optionsChange" :value="index" :range="options" :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ff6f9f50","color":"#666","borderRadius":"40rpx","flex":"1","borderWidth":"2rpx","background":"#ffffff90","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}'>
						<view class="uni-picker-type">{{options[index]}}</view>
					</picker>
				</view>
				

				
				<button v-if="loginType==1||loginType==3||loginType==4" class="btn-submit" @tap="onLoginTap" type="primary" :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 0 40rpx 0","color":"#fff","borderRadius":"40rpx","background":"#ff6f9f","display":"inline-block","width":"100%","lineHeight":"88rpx","fontSize":"32rpx","minWidth":"300rpx","height":"88rpx"}'>登录</button>
				<button v-if="loginType==2" class="btn-submit" @tap="onFaceLoginTap" type="primary" :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 0 40rpx 0","color":"#fff","borderRadius":"40rpx","background":"#ff6f9f","display":"inline-block","width":"100%","lineHeight":"88rpx","fontSize":"32rpx","minWidth":"300rpx","height":"88rpx"}'>人脸识别登录</button>
				<view class="links" :style='{"padding":"0","margin":"20rpx 0 0 0","flexWrap":"wrap","display":"inline-flex","width":"100%","justifyContent":"center","height":"auto"}'>
					<view class="link-highlight" @tap="onRegisterTap('yonghu')" :style='{"padding":"0 20rpx","margin":"0 0 40rpx 0","color":"#ff6f9f","borderRadius":"40rpx","textAlign":"center","background":"#ffffff","display":"inline-block","width":"100%","fontSize":"32rpx","lineHeight":"80rpx","height":"80rpx"}'>注册用户</view>
					<view class="link-highlight" @tap="onRegisterTap('fuwurenyuan')" :style='{"padding":"0 20rpx","margin":"0 0 40rpx 0","color":"#ff6f9f","borderRadius":"40rpx","textAlign":"center","background":"#ffffff","display":"inline-block","width":"100%","fontSize":"32rpx","lineHeight":"80rpx","height":"80rpx"}'>注册服务人员</view>
					<view v-if="loginType==1" @tap="changeLogin(4)" :style='{"padding":"0 20rpx 0 20rpx","margin":"0 0 20rpx 0","color":"#333","borderRadius":"40rpx","textAlign":"right","background":"none","display":"inline-block","width":"100%","fontSize":"28rpx","lineHeight":"80rpx","height":"80rpx"}'>手机号登录</view>
					<view v-if="loginType==2||loginType==3||loginType==4" @tap="changeLogin(1)" :style='{"padding":"0 20rpx 0 20rpx","margin":"0 0 20rpx 0","color":"#333","borderRadius":"40rpx","textAlign":"right","background":"none","display":"inline-block","width":"100%","fontSize":"28rpx","lineHeight":"80rpx","height":"80rpx"}'>用户密码登录</view>
				</view>
				
				<view class="idea1" :style='{"margin":" 0 0 40rpx 0","textAlign":"center","background":"none","display":"block","width":"100%","fontSize":"52rpx","height":"80rpx","order":"-1"}'>欢迎登录</view>
				<view class="idea2" :style='{"width":"100%","background":"#ff0000","display":"none","height":"80rpx"}'>idea2</view>
				<view class="idea3" :style='{"width":"100%","background":"#ff0000","display":"none","height":"80rpx"}'>idea3</view>
			</view>
		</view>
	</view>
</template>

<script>
	import menu from '@/utils/menu'
	export default {
		data() {
			return {
				username: '',
				password: '',
				loginType:1,
				phone: '',
				phonecode: '',
				phoneroles: [
					{
						tableName: '',
						roleName: '请选择登录用户类型'
					},
					{
						tableName: 'yonghu',
						roleName: '用户'
					},
					{
						tableName: 'fuwurenyuan',
						roleName: '服务人员'
					},
				],
				phonerolesIndex: 0,
				// 倒计时时间
				count: 60,
				// 倒计时定时器
				inter: null,
				// 倒计时是否结束
				isEndFlag: true,
				codes: [{
					num: 1,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 2,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 3,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}, {
					num: 4,
					color: '#000',
					rotate: '10deg',
					size: '16px'
				}],
				options: [
					'请选择登录用户类型',
				],
				optionsValues: [
					'',
					'yonghu',
					'fuwurenyuan',
				],
				index: 0,
				roleNum:0,

				indexBgUrl: '',
				indexLogoUrl: '',
			}
		},
		computed: {
			emailText: function() {
				var time = this.count;
				if (null != time && "" != time) {
					time = parseInt(time) + "秒后重发";
				}
				return time;
			}
		},
		onLoad() {
			let options = ['请选择登录用户类型'];
			let menus = menu.list();
			this.menuList = menus;
			for(let i=0;i<this.menuList.length;i++){
				if(this.menuList[i].hasFrontLogin=='是'){
					options.push(this.menuList[i].roleName);
					this.roleNum++;
				}
			}
			if(this.roleNum==1) {
				this.index = 1;
			}	
			this.options = options;
			this.styleChange()
			
		},
		onShow() {
		},
		mounted() {
		},
		methods: {
			async phoneClick() {
				var roles = ''
				if(!this.phone){
					this.$utils.msg(`手机号不能为空`);
					return
				}
				if(this.phone&&(!this.$validate.isMobile(this.phone))){
					this.$utils.msg(`请输入正确的手机号格式`);
					return
				}
				if (this.phoneroles.length>2) {
					if (!this.phoneroles[this.phonerolesIndex].tableName) {
						this.$utils.msg("请选择登录用户类型");
						return false;
					}
					roles = this.phoneroles[this.phonerolesIndex].tableName
				}else {
					roles = this.phoneroles[1].tableName
				}
				var _this = this;
				let res = await this.$api.getPublic(`${roles}/sendsms/login?mobile=${this.phone}`);
				if(res.code==0) {
					this.isEndFlag = false;
					this.inter = setInterval(function() {
						_this.count = _this.count - 1;
						if (_this.count <= 0) {
							clearInterval(_this.inter);
							_this.isEndFlag = true;
							_this.count=60;
						}
					}, 1000);
					this.$utils.msg("发送成功！");
				}else {
					this.$utils.msg(res.msg);
				}
			},
			async phoneLogin() {
				var roles = ''
				var rolesName = ''
				if (this.phoneroles.length>2) {
					roles = this.phoneroles[this.phonerolesIndex].tableName
					rolesName = this.phoneroles[this.phonerolesIndex].roleName
				}else {
					roles = this.phoneroles[1].tableName
					rolesName = this.phoneroles[1].roleName
				}
				let res = await this.$api.postPublic(`${roles}/sms/login?mobile=${this.phone}&&smscode=${this.phonecode}`);
				uni.removeStorageSync("useridTag");
				uni.setStorageSync("appToken", res.token);
				uni.setStorageSync("nickname",res.username);
				uni.setStorageSync("nowTable", `${roles}`);
				res = await this.$api.session(`${roles}`);
				if(res.data.touxiang) {
					uni.setStorageSync('frontHeadportrait', res.data.touxiang);
				} else if(res.data.headportrait) {
					uni.setStorageSync('frontHeadportrait', res.data.headportrait);
				}
				uni.setStorageSync('userSession',JSON.stringify(res.data))
				// 保存用户id
				uni.setStorageSync("appUserid", res.data.id);
				if(res.data.vip) {
					uni.setStorageSync("vip", res.data.vip);
				}
				uni.setStorageSync("appRole", `${rolesName}`);
				this.$utils.tab('../index/index');
			},
			phonerolesChange(e){
				this.phonerolesIndex = e.target.value
			},
			async styleChange() {
				let rs = await this.$api.getPublic('config/info?name=appLoginBackgroundImg')
				this.indexBgUrl = rs.data?rs.data.value:''
				rs = await this.$api.getPublic('config/info?name=appLoginLogo')
				this.indexLogoUrl = rs.data?rs.data.value:''
			},
			onRegisterTap(tableName) {
				uni.setStorageSync("loginTable", tableName);
				this.$utils.jump('../register/register')
			},
			async onLoginTap() {
				if(this.loginType==1) {
					if (!this.username) {
						this.$utils.msg('请输入用户名')
						return
					}
					if (!this.password) {
						this.$utils.msg('请输入用户密码')
						return
					}
					if (!this.optionsValues[this.index]) {
						this.$utils.msg('请选择登录用户类型')
						return
					}
				}
				if(this.loginType==4) {
					if (!this.phone) {
						this.$utils.msg('请输入手机号')
						return
					}
					if (!this.phonecode) {
						this.$utils.msg('请输入手机验证码')
						return
					}
					if (this.phoneroles.length>2&&!this.phoneroles[this.phonerolesIndex].tableName) {
						this.$utils.msg('请选择登录用户类型')
						return
					}
				}

				this.loginPost()

			},
			async loginPost() {
				let that = this
				if(this.loginType==4) {
					this.phoneLogin()
					return false
				}
				let res = await this.$api.login(`${this.optionsValues[this.index]}`, {
					username: this.username,
					password: this.password
				});
				uni.removeStorageSync("useridTag");
				uni.setStorageSync("appToken", res.token);
				uni.setStorageSync("nickname",this.username);
				uni.setStorageSync("nowTable", `${this.optionsValues[this.index]}`);
				res = await this.$api.session(`${this.optionsValues[this.index]}`);
				if(res.data.touxiang) {
					uni.setStorageSync('frontHeadportrait', res.data.touxiang);
				} else if(res.data.headportrait) {
					uni.setStorageSync('frontHeadportrait', res.data.headportrait);
				}
				uni.setStorageSync('userSession',JSON.stringify(res.data))
				// 保存用户id
				uni.setStorageSync("appUserid", res.data.id);
				if(res.data.vip) {
					uni.setStorageSync("vip", res.data.vip);
				}
				uni.setStorageSync("appRole", `${this.options[this.index]}`);
				this.$utils.tab('../index/index');
			},
			changeLogin(type){
				this.loginType = type
			},
			optionsChange(e) {
				this.index = e.target.value
			}
		}
	}
</script>

<style lang="scss" scoped>
	page {
		height: 100%;
	}
	
	.content {
		height: 100%;
		box-sizing: border-box;
	}
	.login-box {
		padding: 160rpx 24rpx 24rpx 24rpx;
		background: radial-gradient(circle at 20% 12%, #fff 0, #fff 90rpx, transparent 220rpx), linear-gradient(180deg, #fff1f5 0%, #ffe6ee 58%, #fff9f6 100%);
		width: 100%;
		min-height: 100%;
		height: auto;
	}
</style>
