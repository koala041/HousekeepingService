<template>
	<div>
		<div class="login-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form class="login_form animate__animated animate__">
				<div class="login_form2">
					<div class="title-container">家政服务O2O平台</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							账号：
						</div>
						<input placeholder="请输入账号：" name="username" type="text" v-model="rulesForm.username">
					</div>
					<div v-if="loginType==1" class="list-item">
						<div class="lable">
							密码：
						</div>
						<div class="password-box">
							<input placeholder="请输入密码：" name="password" :type="showPassword?'text':'password'" v-model="rulesForm.password">
							<span class="icon iconfont" :class="showPassword?'icon-liulan13':'icon-liulan17'" @click="showPassword=!showPassword"></span>
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
				indexBgUrl: '',
			};
		},
		mounted() {
			this.getMenu();

		},
		created() {
			this.$http.get('config/info?name=bLoginBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
		},
		destroyed() {
		},
		components: {
		},
		methods: {
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
				}).then(({
					data
				}) => {
					if (data && data.code === 0) {
						this.menus = JSON.parse(data.data.list[0].menujson);
						for (let i = 0; i < this.menus.length; i++) {
							if (this.menus[i].hasBackLogin=='是') {
								this.roles.push(this.menus[i])
							}
						}
						this.$storage.set("menus", this.menus);
					}
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
					} else {
						this.tableName = this.roles[0].tableName;
						this.rulesForm.role = this.roles[0].roleName;
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
								this.$router.replace({ path: "/" });
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
	background-repeat: no-repeat;
	background-position: center center;
	background-size: cover;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	background-repeat: no-repeat;
	background-size: 100% 100% !important;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: center;
	background-position: center top;

	.login_form {
		padding: 0;
		margin: 20px 13% 0 auto;
		z-index: 1;
		display: flex;
		min-height: 500px;
		flex-wrap: wrap;
		border-radius: 16px;
		box-shadow: none;
		align-content: center;
		background: #fff;
		width: 500px;
		align-items: center;
		height: auto;
		.login_form2 {
			width: 100%;
		}
		.title-container {
			margin: 20px 0 20px 0;
			text-shadow: none;
			color: #2dc7a5;
			font-weight: 600;
			width: auto;
			font-size: 18px;
			line-height: 1;
			text-align: left;
			order: -2;
		}
		.list-item {
			border: 1px solid #eeeeee;
			border-radius: 30px;
			padding: 0 10px;
			margin: 0 auto 20px;
			display: flex;
			width: 100%;
			align-items: center;
			flex-wrap: wrap;
			.lable {
				color: #333;
				width: auto;
				font-size: 14px;
				line-height: 44px;
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
				}
				.iconfont {
					cursor: pointer;
					z-index: 1;
					color: #000;
					top: 0;
					font-size: 16px;
					line-height: 44px;
					position: absolute;
					right: 10px;
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
				border-color: rgba(64, 158, 255, 1);
			}
			::v-deep .el-radio__label {
				color: #666666;
				font-size: 14px;
			}
			::v-deep .el-radio__input.is-checked+.el-radio__label {
				color: rgba(64, 158, 255, 1);
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
				background: #2dc7a3;
				width: 100%;
				font-size: 16px;
				height: 40px;
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

</style>
