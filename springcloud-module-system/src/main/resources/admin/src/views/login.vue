<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<!-- 纯轮播图 无任何卡片背景 -->
			<div class="login-carousel-panel animate__animated animate__fadeInLeft">
				<el-carousel height="500px" :interval="4200" arrow="hover">
					<el-carousel-item v-for="item in carouselSlides" :key="item.src">
						<img class="carousel-img" :src="item.src" :alt="item.title">
					</el-carousel-item>
				</el-carousel>
			</div>

			<el-form class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="brand-badge">Home Service Admin</div>
					<div class="title-container">家政服务O2O平台</div>
					<div class="sub-title">后台管理登录</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							账号：
						</div>
						<input placeholder="请输入用户账号" name="username" type="text" v-model="rulesForm.username">
					</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							密码：
						</div>
						<div class="password-box">
							<input placeholder="请输入用户密码" name="password" :type="showPassword?'text':'password'" v-model="rulesForm.password">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
						</div>
					</div>
					<div v-if="loginType==1" class="list-item captcha-item">
						<div class="lable">
							验证码：
						</div>
						<input placeholder="请输入验证码" maxlength="4" type="text" v-model.trim="captchaInput">
						<div class="captcha-code" @click="refreshCaptcha">
							<span v-for="(item, index) in captchaChars" :key="index" :style="{color: item.color, transform: item.rotate}">{{item.char}}</span>
						</div>
					</div>

					<div class="list-item " v-if="roles.length>1&&loginType<=2">
						<div class="lable">
							角色：
						</div>
						<div prop="loginInRole" class="list-type">
							<el-radio v-if="loginType==1||(loginType==2&&item.role!='users')" v-for="item in roles" v-bind:key="item.roleName" v-model="rulesForm.role" :label="item.roleName">{{item.roleName}}</el-radio>
						</div>
					</div>

					<div class="login-btn">
						<div class="login-btn1">
							<el-button v-if="loginType==1||loginType==3||loginType==4" type="primary" @click="login()" class="loginInBt">登录</el-button>
						</div>
						<div class="login-btn2">
						</div>
						<div class="login-btn3">
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css'
	import menu from "@/utils/menu";
	export default {
		data() {
			return {
				verifyCheck2: false,
				flag: false,
				baseUrl:this.$base.url,
				loginType: 1,
				rulesForm: {
					username: "",
					password: "",
					role: "",
				},
				menus: [],
				roles: [],
				tableName: "",
				showPassword: false,
				captchaInput: '',
				captchaCode: '',
				carouselSlides: [
					{
						src: '/systemPhotos/登录系统轮播图1.png',
					},
					{
						src: '/systemPhotos/登录系统轮播图2.png',
					},
					{
						src: '/systemPhotos/登录系统轮播图3.jpg',
					}
				],
				indexBgUrl: '',
			};
		},
		computed: {
			captchaChars() {
				const colors = ['#2dc7a3', '#ff6f9f', '#ffaa33', '#5068d8'];
				return this.captchaCode.split('').map((char, index) => ({
					char,
					color: colors[index % colors.length],
					rotate: `rotate(${index % 2 === 0 ? '-' : ''}${8 + index * 4}deg)`
				}));
			}
		},
		mounted() {
			this.getMenu();
			this.refreshCaptcha();

		},
		created() {
			this.$http.get('config/info?name=bLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''}).catch(() => {})
		},
		destroyed() {
		},
		components: {
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
			getMenu() {
				let params = {
					page: 1,
					limit: 1,
					sort: 'id',
				}

				this.$http({
					url: "menu/list",
					method: "get",
					params: params
				}).then(({ data }) => {
					if (data && data.code === 0) {
						let list = data.data && data.data.list ? data.data.list : [];
						if (!list.length || !list[0].menujson) {
							this.$message.error('菜单数据为空，请初始化数据库 menu 表');
							return;
						}
						try {
							this.menus = JSON.parse(list[0].menujson);
						} catch (e) {
							this.$message.error('菜单数据格式错误，请检查 menu 表 menujson 字段');
							return;
						}
						this.roles = [];
						for (let i = 0; i < this.menus.length; i++) {
							if (this.menus[i].hasBackLogin=='是' || this.menus[i].tableName=='users' || this.menus[i].roleName=='管理员') {
								this.roles.push(this.menus[i])
							}
						}
						if (this.roles.length === 1) {
							this.rulesForm.role = this.roles[0].roleName;
						}
						this.$storage.set("menus", this.menus);
					}
				}).catch(() => {
					this.$message.error('菜单数据加载失败，请检查后端服务是否正常');
				})
			},

			//注册
			register(tableName){
				this.$storage.set("loginTable", tableName);
				this.$router.push({path:'/register',query:{pageFlag:'register'}})
			},
			// 登陆
			login() {
				if(this.loginType==1) {

					if (!this.rulesForm.username) {
						this.$message.error("请输入用户名");
						return;
					}
					if (!this.rulesForm.password) {
						this.$message.error("请输入密码");
						return;
					}
					if (!this.captchaInput) {
						this.$message.error("请输入验证码");
						return;
					}
					if (this.captchaInput.toUpperCase() !== this.captchaCode) {
						this.$message.error("验证码错误，请重新输入");
						this.refreshCaptcha();
						return;
					}
					if(this.roles.length>1) {
						if (!this.rulesForm.role) {
							this.$message.error("请选择角色");
							return;
						}
					
						for (let i = 0; i < this.roles.length; i++) {
							if (this.roles[i].roleName == this.rulesForm.role) {
								this.tableName = this.roles[i].tableName;
							}
						}
					} else if (this.roles.length === 1) {
						this.tableName = this.roles[0].tableName;
						this.rulesForm.role = this.roles[0].roleName;
					} else {
						this.$message.error('暂无可登录角色，请检查后台菜单配置');
						return;
					}
				}
		
				this.loginPost()
			},
			loginPost() {
				this.$http({
					url: `${this.tableName}/login?username=${this.rulesForm.username}&password=${this.rulesForm.password}`,
					method: "post"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.$storage.set("Token", data.token);
						this.$storage.set("role", this.rulesForm.role);
						this.$storage.set("sessionTable", this.tableName);
						this.$storage.set("adminName", this.rulesForm.username);
						this.$nextTick(()=>{
							this.$http({
								url: this.tableName + '/session',
								method: "get"
							}).then(({
								data
							}) => {
								if (data && data.code === 0) {
									if(this.tableName == 'yonghu') {
										this.$storage.set('headportrait',data.data.touxiang)
									}
									if(this.tableName == 'fuwurenyuan') {
										this.$storage.set('headportrait',data.data.touxiang)
									}
									if(this.tableName == 'users') {
										this.$storage.set('headportrait',data.data.image)
									}
									this.$storage.set('userForm',JSON.stringify(data.data))
									this.$storage.set('userid',data.data.id);
								} else {
									let message = this.$message
									message.error(data.msg);
								}
								this.$router.replace({ path: "/index" });
							});
						})
					}
					else {
						this.$message.error(data.msg);
					}
				});
			},
		}
	}
</script>

<style lang="scss" scoped>
.login-container {
	min-height: 100vh;
	position: relative;
	background: radial-gradient(circle at 16% 15%, rgba(255,255,255,.95) 0, rgba(255,255,255,.88) 85px, transparent 230px), radial-gradient(circle at 83% 22%, rgba(45,199,163,.22) 0, rgba(45,199,163,.08) 185px, transparent 360px), linear-gradient(135deg, #fff7f3 0%, #fff0f5 46%, #eafff8 100%);
	background-repeat: no-repeat;
	background-size: cover !important;
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: space-between;
	align-items: center;
	gap: 48px;
	padding: 40px 9%;
	box-sizing: border-box;
	background-position: center top;
	overflow: hidden;
	&::before {
		content: 'HOUSEKEEPING';
		position: absolute;
		left: 8%;
		bottom: 9%;
		font-size: 72px;
		font-weight: 800;
		letter-spacing: 10px;
		color: rgba(255, 111, 159, .08);
	}
	&::after {
		content: '';
		position: absolute;
		right: -120px;
		top: -80px;
		width: 360px;
		height: 360px;
		border-radius: 44% 56% 40% 60%;
		background: rgba(255, 111, 159, .15);
	}

/* 纯轮播图样式 无任何卡片背景 - 放大版 */
.login-carousel-panel {
  position: relative;
  z-index: 1;
  width: min(65vw, 900px); /* 更宽 */
  min-width: 600px;        /* 最小宽度更大 */
  padding: 0;
  border-radius: 0;
  background: transparent;
  border: none;
  box-shadow: none;
  backdrop-filter: none;
  margin-top: -60px;

  &::before,
  &::after {
    display: none;
  }

  .carousel-img {
    width: 100%;
    height: 480px; /* 图片高度大幅加大 */
    object-fit: cover;
    border-radius: 20px; /* 圆角更协调 */
  }

  ::v-deep .el-carousel__container {
    height: 480px !important; /* 强制轮播容器高度匹配 */
    border-radius: 20px;
    background: transparent;
  }
  ::v-deep .el-carousel__item {
    background: transparent;
  }
}

	.login_form {
		padding: 40px 46px 34px;
		margin: 20px 0 0 auto;
		z-index: 1;
		display: flex;
		min-height: 560px;
		flex-wrap: wrap;
		border-radius: 28px;
		box-shadow: 0 28px 70px rgba(255, 111, 159, .18), 0 12px 35px rgba(45,199,163,.12);
		align-content: center;
		border: 1px solid rgba(255, 111, 159, .12);
		background: rgba(255,255,255,.9);
		width: 500px;
		align-items: center;
		height: auto;
		backdrop-filter: blur(10px);
		.login_form2 {
			width: 100%;
		}
		.brand-badge {
			display: inline-block;
			padding: 8px 14px;
			border-radius: 999px;
			color: #ff6f9f;
			background: #fff1f5;
			font-size: 12px;
			font-weight: 700;
			letter-spacing: 1px;
		}
		.title-container {
			margin: 18px 0 10px 0;
			text-shadow: none;
			color: #24332f;
			font-weight: 800;
			width: auto;
			font-size: 28px;
			line-height: 1;
			text-align: left;
			order: -2;
		}
		.sub-title {
			margin: 0 0 28px;
			color: #7b6870;
			font-size: 14px;
		}
		.list-item {
			border: 1px solid rgba(255, 111, 159, .18);
			border-radius: 30px;
			padding: 0 14px;
			margin: 0 auto 20px;
			display: flex;
			width: 100%;
			align-items: center;
			flex-wrap: wrap;
			background: rgba(255,255,255,.72);
			transition: border-color .2s ease, box-shadow .2s ease, background .2s ease;
			&:focus-within {
				border-color: rgba(255,111,159,.55);
				box-shadow: 0 0 0 4px rgba(255,111,159,.1);
				background: #fff;
			}
			.lable {
				color: #4b4b4b;
				width: auto;
				font-size: 14px;
				line-height: 44px;
				font-weight: 600;
			}
			input {
				border: 0px solid #ddd;
				border-radius: 8px;
				padding: 0 0 0 10px;
				color: #333;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 42px;
				outline: none !important;
				box-shadow: none !important;
			}
			input:focus {
				border: none;
				border-radius: 8px;
				padding: 0 0 0 10px;
				box-shadow: none;
				color: #333;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 42px;
				outline: none !important;
			}
			.password-box {
				padding: 0 10px;
				flex: 1;
				display: flex;
				width: 100%;
				position: relative;
				align-items: center;
				input {
					border: 0px solid #ddd;
					border-radius: 8px;
					padding: 0;
					color: #333;
					background: none;
					width: 100%;
					font-size: 14px;
					height: 44px;
					outline: none !important;
					box-shadow: none !important;
				}
				input:focus {
					border: none;
					border-radius: 8px;
					padding: 0;
					box-shadow: none;
					color: #333;
					background: none;
					width: 100%;
					font-size: 14px;
					height: 44px;
					outline: none !important;
				}
				.iconfont {
					cursor: pointer;
					z-index: 1;
					color: #ff6f9f;
					top: 0;
					font-size: 16px;
					line-height: 44px;
					position: absolute;
					right: 10px;
				}
			}
			&.captcha-item {
				flex-wrap: nowrap;
				input {
					min-width: 0;
				}
			}
			.captcha-code {
				cursor: pointer;
				width: 116px;
				height: 38px;
				border-radius: 20px;
				background: repeating-linear-gradient(-35deg, rgba(255,111,159,.12) 0, rgba(255,111,159,.12) 8px, rgba(45,199,163,.12) 8px, rgba(45,199,163,.12) 16px), #fff;
				display: flex;
				align-items: center;
				justify-content: center;
				box-shadow: inset 0 0 0 1px rgba(255,111,159,.16);
				span {
					margin: 0 3px;
					display: inline-block;
					font-size: 20px;
					font-weight: 800;
					letter-spacing: 1px;
					user-select: none;
				}
			}
			input::placeholder {
				color: #9ca2ae;
				font-size: 14px;
			}
		}
		.list-type {
			padding: 0 10px;
			margin: 0 auto;
			flex: 1;
			width: 100%;
			::v-deep .el-radio__input .el-radio__inner {
				background: rgba(53, 53, 53, 0);
				border-color: #666666;
			}
			::v-deep .el-radio__input.is-checked .el-radio__inner {
				background: rgba(64, 158, 255, 1);
				border-color: #2dc7a3;
				background: #2dc7a3;
			}
			::v-deep .el-radio__label {
				color: #666666;
				font-size: 14px;
			}
			::v-deep .el-radio__input.is-checked+.el-radio__label {
				color: #2dc7a3;
				font-size: 14px;
			}
		}
		.login-btn {
			margin: 20px auto;
			display: flex;
			width: 100%;
			justify-content: center;
			align-items: center;
			flex-wrap: wrap;
			.login-btn1 {
				width: 100%;
			}
			.login-btn2 {
				width: auto;
			}
			.login-btn3 {
				margin: 0 0 0 auto;
				width: auto;
			}
			.loginInBt {
				border: 0;
				cursor: pointer;
				border-radius: 30px;
				padding: 0 24px;
				margin: 0 0 20px;
				outline: none;
				color: #fff;
				background: linear-gradient(135deg, #2dc7a3 0%, #ff8fb0 100%);
				width: 100%;
				font-size: 16px;
				height: 44px;
				box-shadow: 0 14px 28px rgba(45,199,163,.22);
				font-weight: 700;
			}
			.loginInBt:hover {
				opacity: 0.8;
			}
			.register {
				border: 0px solid #333;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 10px;
				margin: 0;
				outline: none;
				color: #666;
				background: none;
				width: auto;
				font-size: 14px;
				height: auto;
			}
			.register:hover {
				color: #2dc7a3;
			}
			.forget {
				border: 0;
				cursor: pointer;
				border-radius: 0;
				padding: 0;
				margin: 0;
				outline: none;
				color: #999;
				background: none;
				width: auto;
				font-size: 14px;
				height: auto;
			}
			.forget:hover {
				opacity: 0.8;
			}
		}
	}
}

@media screen and (max-width: 1180px) {
	.login-container {
		padding: 32px 5%;
		gap: 28px;
		.login-carousel-panel {
			min-width: 0;
			width: 52vw;
		}
		.login_form {
			width: 450px;
		}
	}
}

@media screen and (max-width: 920px) {
	.login-container {
		justify-content: center;
		padding: 28px 20px;
		.login-carousel-panel {
			display: none;
		}
		.login_form {
			width: min(500px, 100%);
			margin: 0;
		}
	}
}
</style>