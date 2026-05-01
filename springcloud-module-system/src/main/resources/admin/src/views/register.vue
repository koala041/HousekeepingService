<template>
	<div>
		<div class="register-container" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
			<el-form v-if="pageFlag=='register'" ref="ruleForm" class="rgs-form animate__animated animate__" :model="ruleForm" :rules="rules">
				<div class="rgs-form2">
					<div class="title">家政服务O2O平台</div>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('yonghuzhanghao')?'required':''">用户账号：</div>
						<el-input  v-model="ruleForm.yonghuzhanghao" :readonly="ro.yonghuzhanghao" autocomplete="off" placeholder="用户账号"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="密码"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="确认密码" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('yonghuxingming')?'required':''">用户姓名：</div>
						<el-input  v-model="ruleForm.yonghuxingming" :readonly="ro.yonghuxingming" autocomplete="off" placeholder="用户姓名"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('xingbie')?'required':''">性别：</div>
						<el-select filterable v-model="ruleForm.xingbie" placeholder="请选择性别" :disabled="ro.xingbie">
							<el-option
								v-for="(item,index) in yonghuxingbieOptions"
								v-bind:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="yonghutouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item email" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mobile')?'required':''">手机号码：</div>
						<div style="display: flex;flex: 1;">
							<input v-model="ruleForm.mobile" autocomplete="off" placeholder="手机号"/>
							<button v-if="isEndFlag" type="success" class="register-code" @click="sendsmscode()">发送验证码</button>
							<button v-if="!isEndFlag" type="success" class="register-code" disabled="disabled">{{SecondToDate}}</button>
						</div>
					</el-form-item>
					<el-form-item class="list-item email-code" v-if="tableName=='yonghu'">
						<div class="lable" :class="changeRules('mobile')?'required':''">验证码：</div>
						<el-input  v-model="smscode" autocomplete="off" placeholder="验证码" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('yuangongzhanghao')?'required':''">员工账号：</div>
						<el-input  v-model="ruleForm.yuangongzhanghao" :readonly="ro.yuangongzhanghao" autocomplete="off" placeholder="员工账号"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('mima')?'required':''">密码：</div>
						<el-input  v-model="ruleForm.mima" :readonly="ro.mima" autocomplete="off" placeholder="密码"  type="password"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('mima')?'required':''">确认密码：</div>
						<el-input  v-model="ruleForm.mima2" autocomplete="off" placeholder="确认密码" type="password" :readonly="ro.mima" />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('yuangongxingming')?'required':''">员工姓名：</div>
						<el-input  v-model="ruleForm.yuangongxingming" :readonly="ro.yuangongxingming" autocomplete="off" placeholder="员工姓名"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('touxiang')?'required':''">头像：</div>
						<file-upload
							tip="点击上传头像"
							action="file/upload"
							:limit="3"
							:multiple="true"
							:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
							@change="fuwurenyuantouxiangUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('fuwujineng')?'required':''">服务技能：</div>
						<el-input  v-model="ruleForm.fuwujineng" :readonly="ro.fuwujineng" autocomplete="off" placeholder="服务技能"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('zizhizhengshu')?'required':''">资质证书：</div>
						<file-upload
							tip="点击上传资质证书"
							action="file/upload"
							:limit="3"
							:type="3"
							:multiple="true"
							:fileUrls="ruleForm.zizhizhengshu?ruleForm.zizhizhengshu:''"
							@change="fuwurenyuanzizhizhengshuUploadChange"
						></file-upload>
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('fuwufanwei')?'required':''">服务范围：</div>
						<el-input  v-model="ruleForm.fuwufanwei" :readonly="ro.fuwufanwei" autocomplete="off" placeholder="服务范围"  type="text"  />
					</el-form-item>
					<el-form-item class="list-item" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('fuwuleixing')?'required':''">服务类型：</div>
						<el-select filterable v-model="ruleForm.fuwuleixing" placeholder="请选择服务类型" :disabled="ro.fuwuleixing">
							<el-option
								v-for="(item,index) in fuwurenyuanfuwuleixingOptions"
								v-bind:key="index"
								:label="item"
								:value="item">
							</el-option>
						</el-select>
					</el-form-item>
					<el-form-item class="list-item email" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('mobile')?'required':''">手机号码：</div>
						<div style="display: flex;flex: 1;">
							<input v-model="ruleForm.mobile" autocomplete="off" placeholder="手机号"/>
							<button v-if="isEndFlag" type="success" class="register-code" @click="sendsmscode()">发送验证码</button>
							<button v-if="!isEndFlag" type="success" class="register-code" disabled="disabled">{{SecondToDate}}</button>
						</div>
					</el-form-item>
					<el-form-item class="list-item email-code" v-if="tableName=='fuwurenyuan'">
						<div class="lable" :class="changeRules('mobile')?'required':''">验证码：</div>
						<el-input  v-model="smscode" autocomplete="off" placeholder="验证码" />
					</el-form-item>
					<div class="register-btn">
						<div class="register-btn1">
							<button type="button" class="r-btn" @click="login()">注册</button>
						</div>
						<div class="register-btn2">
							<div class="r-login" @click="close()">已有账号，直接登录</div>
						</div>
					</div>
				</div>
			</el-form>
		</div>
	</div>
</template>

<script>
	import 'animate.css'
export default {
	data() {
		return {
			ruleForm: {
			},
			forgetForm: {},
            pageFlag : '',
			tableName:"",
			rules: {},
			ro: {},
            smscode:'',
			// 倒计时时间
			count: 60,
			// 倒计时定时器
			inter: null,
			// 倒计时是否结束
			isEndFlag: true,
            yonghuxingbieOptions: [],
            fuwurenyuanfuwuzhuangtaiOptions: [],
            fuwurenyuanfuwuleixingOptions: [],
			indexBgUrl: '',
		};
	},
	computed: {
		SecondToDate: function() {
			var time = this.count;
			if (null != time && "" != time) {
				time = parseInt(time) + "秒后重发";
			}
			return time;
		}
	},
	mounted(){
		this.pageFlag = this.$route.query.pageFlag
		if(this.$route.query.pageFlag=='register'){
			
			let table = this.$storage.get("loginTable");
			this.tableName = table;
			if(this.tableName=='yonghu'){
				this.ruleForm = {
					yonghuzhanghao: '',
					mima: '',
					yonghuxingming: '',
					xingbie: '',
					touxiang: '',
					mobile: '',
					status: '',
				}
				this.ro = {
					yonghuzhanghao: false,
					mima: false,
					yonghuxingming: false,
					xingbie: false,
					touxiang: false,
					mobile: false,
					status: false,
				}
			}
			if(this.tableName=='fuwurenyuan'){
				this.ruleForm = {
					yuangongzhanghao: '',
					mima: '',
					yuangongxingming: '',
					touxiang: '',
					fuwujineng: '',
					fuwuzhuangtai: '空闲',
					shouyi: '0',
					zizhizhengshu: '',
					fuwufanwei: '',
					fuwuleixing: '',
					mobile: '',
					status: '',
				}
				this.ro = {
					yuangongzhanghao: false,
					mima: false,
					yuangongxingming: false,
					touxiang: false,
					fuwujineng: false,
					fuwuzhuangtai: true,
					shouyi: true,
					zizhizhengshu: false,
					fuwufanwei: false,
					fuwuleixing: false,
					mobile: false,
					status: false,
				}
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuzhanghao = [{ required: true, message: '请输入用户账号', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('yonghu' == this.tableName) {
				this.rules.yonghuxingming = [{ required: true, message: '请输入用户姓名', trigger: 'blur' }]
			}
			if ('fuwurenyuan' == this.tableName) {
				this.rules.yuangongzhanghao = [{ required: true, message: '请输入员工账号', trigger: 'blur' }]
			}
			if ('fuwurenyuan' == this.tableName) {
				this.rules.mima = [{ required: true, message: '请输入密码', trigger: 'blur' }]
			}
			if ('fuwurenyuan' == this.tableName) {
				this.rules.yuangongxingming = [{ required: true, message: '请输入员工姓名', trigger: 'blur' }]
			}
			this.yonghuxingbieOptions = "男,女".split(',')
			this.fuwurenyuanfuwuzhuangtaiOptions = "空闲,工作中".split(',')
			this.$http({
				url: `option/fuwuleixing/fuwuleixing`,
				method: "get",
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.fuwurenyuanfuwuleixingOptions = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
		}
	},
	created() {
		this.$http.get('config/info?name=bRegisterBackgroundImg',).then(rs=>{this.indexBgUrl = rs.data.data?rs.data.data.value:''})
	},
	destroyed() {
		  	},
	methods: {
		changeRules(name){
			if(this.rules[name]){
				return true
			}
			return false
		},
		// 获取uuid
		getUUID () {
			return new Date().getTime();
		},
		close(){
			this.$router.push({ path: "/login" });
		},
        yonghutouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },
        fuwurenyuantouxiangUploadChange(fileUrls) {
            this.ruleForm.touxiang = fileUrls;
        },
        fuwurenyuanzizhizhengshuUploadChange(fileUrls) {
            this.ruleForm.zizhizhengshu = fileUrls;
        },

        // 多级联动参数

		sendsmscode() {
			if(!this.ruleForm.mobile){
				this.$message.error(`手机号码不能为空`);
				return
			}
			if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
				this.$message.error(`请输入正确的手机格式`);
				return
			}
			this.isEndFlag = false;
			var _this = this;
			this.inter = window.setInterval(function() {
				_this.count = _this.count - 1;
				if (_this.count <= 0) {
					window.clearInterval(_this.inter);
					_this.isEndFlag = true;
					_this.count=60;
				}
			}, 1000);
			this.$http({
				url: `${this.tableName}/sendsms?mobile=`+this.ruleForm.mobile,
				method: "get",
				params: {}
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message.success(`发送成功`);
				} else {
					this.$message.error(data.msg)
				}
			});
		},

		// 注册
		login() {
			var url=this.tableName+"/register";
			if((!this.ruleForm.yonghuzhanghao) && `yonghu` == this.tableName){
				this.$message.error(`用户账号不能为空`);
				return
			}
			if((!this.ruleForm.mima) && `yonghu` == this.tableName){
				this.$message.error(`密码不能为空`);
				return
			}
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `yonghu` == this.tableName){
				this.$message.error(`两次密码输入不一致`);
				return
			}
			if((!this.ruleForm.yonghuxingming) && `yonghu` == this.tableName){
				this.$message.error(`用户姓名不能为空`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
			if(`yonghu` == this.tableName && this.ruleForm.mobile &&(!this.$validate.isMobile(this.ruleForm.mobile))){
				this.$message.error(`手机号应输入手机格式`);
				return
			}
			if(`yonghu` == this.tableName ){
				url=this.tableName+"/register?smscode="+this.smscode;
				if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$message.error(`请输入正确的手机格式`);
					return
				}
			}
			if((!this.smscode) && `yonghu` == this.tableName){
				this.$message.error(`验证码不能为空`);
				return
			}
			if((!this.ruleForm.yuangongzhanghao) && `fuwurenyuan` == this.tableName){
				this.$message.error(`员工账号不能为空`);
				return
			}
			if((!this.ruleForm.mima) && `fuwurenyuan` == this.tableName){
				this.$message.error(`密码不能为空`);
				return
			}
			if((this.ruleForm.mima!=this.ruleForm.mima2) && `fuwurenyuan` == this.tableName){
				this.$message.error(`两次密码输入不一致`);
				return
			}
			if((!this.ruleForm.yuangongxingming) && `fuwurenyuan` == this.tableName){
				this.$message.error(`员工姓名不能为空`);
				return
			}
            if(this.ruleForm.touxiang!=null) {
                this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
            }
            if(this.ruleForm.zizhizhengshu!=null) {
                this.ruleForm.zizhizhengshu = this.ruleForm.zizhizhengshu.replace(new RegExp(this.$base.url,"g"),"");
            }
			if(`fuwurenyuan` == this.tableName && this.ruleForm.mobile &&(!this.$validate.isMobile(this.ruleForm.mobile))){
				this.$message.error(`手机号应输入手机格式`);
				return
			}
			if(`fuwurenyuan` == this.tableName ){
				url=this.tableName+"/register?smscode="+this.smscode;
				if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$message.error(`请输入正确的手机格式`);
					return
				}
			}
			if((!this.smscode) && `fuwurenyuan` == this.tableName){
				this.$message.error(`验证码不能为空`);
				return
			}
			this.$http({
				url: url,
				method: "post",
				data:this.ruleForm
			}).then(({ data }) => {
				if (data && data.code === 0) {
					this.$message({
						message: "注册成功",
						type: "success",
						duration: 1500,
						onClose: () => {
							this.$router.replace({ path: "/login" });
						}
					});
				} else {
					this.$message.error(data.msg);
				}
			});
		},
	}
};
</script>

<style lang="scss" scoped>
.register-container {
	position: relative;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	background-repeat: no-repeat;
	background-size: cover !important;
	background: url(http://codegen.caihongy.cn/20260126/46027c3659c94fe2a567a779cd10a4ca.jpg);
	display: flex;
	width: 100%;
	min-height: 100vh;
	justify-content: center;
	align-items: center;
	background-position: top center;
	.rgs-form {
		.rgs-form2 {
		width: 100%;
		}
		padding: 0;
		margin: 20px 13% 20px auto;
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
		.title {
			margin: 0 0 20px 0;
			text-shadow: none;
			color: #2dc7a3;
			font-weight: 600;
			width: auto;
			font-size: 18px;
			line-height: 1;
			text-align: center;
			order: -2;
		}
		.list-item {
			border: 1px solid #eeeeee;
			border-radius: 30px;
			padding: 0 0 0 130px;
			margin: 0 auto 15px;
			width: 100%;
			position: relative;
			height: auto;
			::v-deep .el-form-item__content {
				display: block;
			}
			.lable {
				padding: 0 10px 0 0;
				color: #333;
				left: -130px;
				width: 130px;
				font-size: 14px;
				line-height: 44px;
				position: absolute !important;
				text-align: right;
			}
			.el-input {
				width: 100%;
			}
			.el-input ::v-deep .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input-number {
				width: 100%;
			}
			.el-input-number ::v-deep .el-input__inner {
				text-align: center;
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input-number ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-input-number ::v-deep .el-input-number__decrease {
				display: none;
			}
			.el-input-number ::v-deep .el-input-number__increase {
				display: none;
			}
			.el-select {
				width: 100%;
			}
			.el-select ::v-deep .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px;
				color: #666;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-select ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor {
				width: 100%;
			}
			.el-date-editor ::v-deep .el-input__inner {
				border: 0px solid #ddd;
				border-radius: 0px;
				padding: 0 10px 0 30px;
				color: #666;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor ::v-deep .el-input__inner:focus {
				border: none;
				border-radius: 0px;
				padding: 0 10px 0 30px;
				box-shadow: none;
				color: #333;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			.el-date-editor.el-input {
				width: 100%;
			}
			::v-deep .el-upload--picture-card {
				background: transparent;
				border: 0;
				border-radius: 0;
				width: auto;
				height: auto;
				line-height: initial;
				vertical-align: middle;
			}
			::v-deep .upload .upload-img {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload-list .el-upload-list__item {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload .el-icon-plus {
				border: 1px solid #ddd;
				cursor: pointer;
				border-radius: 8px;
				margin: 5px 0 0 0;
				color: #999;
				width: 60px;
				font-size: 24px;
				line-height: 60px;
				text-align: center;
				height: 60px;
			}
			::v-deep .el-upload__tip {
				margin: 0;
				color: #838fa1;
				line-height: 24px;
			}
			::v-deep .el-input__inner::placeholder {
				color: #999;
				font-size: 14px;
			}
			.required {
				position: relative;
			}
			.required::after{
				color: red;
				left: 120px;
				position: absolute;
				content: "*";
			}
			.editor {
				border-radius: 10px;
				margin: 10px;
				width: auto;
				height: auto;
			}
			.editor>.avatar-uploader {
				line-height: 0;
				height: 0;
			}
		}
		.list-item.email {
			input {
				border: 0px solid #ddd;
				border-radius: 0px 0 0 0px;
				padding: 0 10px;
				box-shadow: none;
				margin: 0;
				outline: none;
				color: #606266;
				background: none;
				flex: 1;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input:focus {
				border: none;
				border-radius: 0px 0 0 0px;
				padding: 0 10px;
				box-shadow: none;
				color: #333;
				flex: 1;
				background: none;
				width: 100%;
				font-size: 14px;
				height: 44px;
			}
			input::placeholder {
				color: #999;
				font-size: 14px;
			}
			button {
				border: 0;
				cursor: pointer;
				border-radius: 0 30px 30px 0;
				padding: 0;
				box-shadow: none;
				margin: 0;
				outline: none;
				color: #333;
				background: #eee;
				width: 110px;
				font-size: 15px;
				height: 44px;
			}
			button:hover {
				opacity: 0.8;
			}
		}
		.register-btn {
			margin: 0 auto;
			width: 100%;
		}
		.register-btn1 {
			width: 100%;
		}
		.register-btn2 {
			margin: 10px 0;
			width: 100%;
			text-align: center;
		}
		.r-btn {
			border: 0;
			cursor: pointer;
			padding: 0 10px;
			margin: 20px auto 5px;
			color: #fff;
			display: block;
			font-size: 16px;
			border-radius: 30px;
			box-shadow: none;
			outline: none;
			background: #2dc7a3;
			width: 100%;
			height: 40px;
		}
		.r-btn:hover {
			opacity: 0.8;
		}
		.r-login {
			cursor: pointer;
			padding: 0 10%;
			color: rgba(159, 159, 159, 1);
			display: inline-block;
			text-decoration: underline;
			font-size: 14px;
			line-height: 1;
		}
		.r-login:hover {
			opacity: 0.5;
		}
	}
}
	
	::-webkit-scrollbar {
	  display: none;
	}
</style>
