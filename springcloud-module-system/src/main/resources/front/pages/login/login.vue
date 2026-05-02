<template>
	<view class="content">
		<view class="login-box" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<view class="login-card" :style='{"padding":"0 40rpx","flexWrap":"wrap","display":"flex","width":"100%","position":"relative","justifyContent":"center","height":"auto"}'>
				<image :style='{"width":"180rpx","margin":"0 auto 48rpx auto","borderRadius":"160rpx","display":"none","height":"180rpx"}' :src="indexLogoUrl?($base.url + indexLogoUrl):'/static/service.png'" mode="aspectFill"></image>
				<view v-if="loginType==1" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' class="uni-form-item uni-column login-row">
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">账号：</view>
					<input v-model="username" :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="text" class="uni-input login-input" name="" placeholder="请输入账号" />
				</view>
				<view v-if="loginType==1" :style='{"border":"0px solid #ff6f9f50","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' class="uni-form-item uni-column login-row">
					<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">密码：</view>
					<input v-model="password" password :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' type="password" class="uni-input login-input" name="" placeholder="请输入密码" />
				</view>
				<view v-if="loginType==1" class="captcha-row">
					<view class="captcha-label">校验码：</view>
					<input v-model.trim="captchaInput" type="text" maxlength="4" class="uni-input login-input captcha-input" placeholder="请输入校验码" />
					<view class="captcha-code" @tap="refreshCaptcha">
						<text v-for="(item, codeIndex) in captchaChars" :key="codeIndex" :style="{transform: item.rotate, color: item.color}">{{item.char}}</text>
					</view>
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
				captchaInput: '',
				captchaCode: '',
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
			captchaChars: function() {
				const colors = ['#ff6f9f', '#2dc7a3', '#ffaa33', '#5b6ee1'];
				return this.captchaCode.split('').map((char, index) => {
					return {
						char,
						color: colors[index % colors.length],
						rotate: `rotate(${index % 2 === 0 ? '-' : ''}${8 + index * 3}deg)`
					}
				})
			},
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
			this.refreshCaptcha();
			this.styleChange()
			
		},
		onShow() {
		},
		mounted() {
		},
		methods: {
			refreshCaptcha() {
				const chars = 'ABCDEFGHJKLMNPQRSTUVWXYZ23456789';
				let code = '';
				for (let i = 0; i < 4; i++) {
					code += chars.charAt(Math.floor(Math.random() * chars.length));
				}
				this.captchaCode = code;
				this.captchaInput = '';
			},
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
					if (!this.captchaInput) {
						this.$utils.msg('请输入校验码')
						return
					}
					if (this.captchaInput.toUpperCase() !== this.captchaCode) {
						this.$utils.msg('校验码错误，请重新输入')
						this.refreshCaptcha()
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
				if(type==1) this.refreshCaptcha()
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
		padding: 110rpx 28rpx 40rpx 28rpx;
		background: radial-gradient(circle at 18% 8%, rgba(255,255,255,.95) 0, rgba(255,255,255,.9) 92rpx, transparent 230rpx), radial-gradient(circle at 86% 18%, rgba(45,199,163,.18) 0, rgba(45,199,163,.08) 170rpx, transparent 330rpx), linear-gradient(160deg, #fff7f3 0%, #fff0f5 48%, #eafff8 100%);
		width: 100%;
		min-height: 100%;
		height: auto;
		box-sizing: border-box;
		overflow: hidden;
		position: relative;
	}
	.login-box::before {
		content: '';
		position: absolute;
		z-index: 0;
		right: -90rpx;
		top: 72rpx;
		width: 300rpx;
		height: 300rpx;
		border-radius: 46% 54% 42% 58%;
		background: rgba(255,111,159,.14);
	}
	.login-box::after {
		content: '安心家政';
		position: absolute;
		z-index: 0;
		left: 52rpx;
		top: 48rpx;
		color: #ff6f9f;
		font-size: 30rpx;
		font-weight: 700;
		letter-spacing: 4rpx;
	}
	.login-card {
		position: relative !important;
		z-index: 1;
		padding: 52rpx 40rpx 44rpx !important;
		border: 2rpx solid rgba(255, 111, 159, .12);
		border-radius: 44rpx;
		background: rgba(255,255,255,.86);
		box-shadow: 0 28rpx 70rpx rgba(255,111,159,.18), 0 12rpx 32rpx rgba(45,199,163,.1);
		backdrop-filter: blur(12rpx);
		box-sizing: border-box;
	}
	.login-row .label,
	.captcha-label {
		color: #4b4b4b !important;
		font-weight: 600;
	}
	.login-input,
	.uni-input {
		outline: none !important;
		-webkit-appearance: none;
		-webkit-tap-highlight-color: transparent;
		box-shadow: none !important;
		box-sizing: border-box;
		transition: border-color .2s ease, box-shadow .2s ease, background .2s ease;
	}
	.login-input:focus,
	.uni-input:focus,
	input:focus {
		outline: none !important;
		border-color: rgba(255,111,159,.55) !important;
		box-shadow: 0 0 0 4rpx rgba(255,111,159,.12) !important;
		background: rgba(255,255,255,.96) !important;
	}
	.captcha-row {
		margin: 0 0 24rpx 0;
		display: flex;
		width: 100%;
		align-items: center;
	}
	.captcha-label {
		white-space: nowrap;
		text-align: right;
		min-width: 144rpx;
		font-size: 28rpx;
		line-height: 88rpx;
		height: 88rpx;
	}
	.captcha-input {
		padding: 0 24rpx;
		border: 2rpx solid rgba(255,111,159,.32);
		border-radius: 40rpx;
		color: #333;
		font-size: 28rpx;
		line-height: 88rpx;
		height: 88rpx;
		background: rgba(255,255,255,.72);
		flex: 1;
	}
	.captcha-code {
		margin-left: 16rpx;
		width: 176rpx;
		height: 88rpx;
		border-radius: 36rpx;
		background: repeating-linear-gradient(-35deg, rgba(255,111,159,.12) 0, rgba(255,111,159,.12) 10rpx, rgba(45,199,163,.12) 10rpx, rgba(45,199,163,.12) 20rpx), #fff;
		display: flex;
		align-items: center;
		justify-content: center;
		box-shadow: inset 0 0 0 2rpx rgba(255,111,159,.16);
	}
	.captcha-code text {
		margin: 0 4rpx;
		font-size: 34rpx;
		font-weight: 800;
		letter-spacing: 2rpx;
		display: inline-block;
	}
	.btn-submit {
		background: linear-gradient(135deg, #ff6f9f 0%, #ff9a76 100%) !important;
		box-shadow: 0 16rpx 32rpx rgba(255,111,159,.26);
		font-weight: 700;
	}
	.link-highlight {
		box-shadow: 0 10rpx 24rpx rgba(255,111,159,.12);
	}
	.idea1 {
		color: #24332f;
		font-weight: 800;
		letter-spacing: 2rpx;
	}
	@media screen and (max-width: 360px) {
		.captcha-code {
			width: 150rpx;
		}
		.captcha-code text {
			font-size: 30rpx;
		}
	}
</style>
