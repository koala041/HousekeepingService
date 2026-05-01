<template>
	<div :style='{"width":"100%","padding":"20px","fontSize":"14px","color":"#666","height":"auto"}'>
		<el-form
			:style='{"border":"0px solid #e5e7eb","padding":"50px 20% 30px 10%","boxShadow":"none","borderRadius":"10px","flexWrap":"wrap","background":"#fff","display":"flex"}'
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			label-width="180px"
		>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="用户账号" prop="yonghuzhanghao">
					<el-input v-model="ruleForm.yonghuzhanghao" :readonly="ro.yonghuzhanghao" placeholder="用户账号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="用户姓名" prop="yonghuxingming">
					<el-input v-model="ruleForm.yonghuxingming" :readonly="ro.yonghuxingming" placeholder="用户姓名" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='yonghu'"  label="性别" prop="xingbie">
					<el-select filterable v-model="ruleForm.xingbie" :disabled="ro.xingbie" placeholder="请选择性别">
						<el-option
							v-for="(item,index) in yonghuxingbieOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='yonghu'" label="头像" prop="touxiang">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="yonghutouxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='yonghu'"  label="手机号" prop="mobile">
					<el-input v-model="ruleForm.mobile" :readonly="ro.mobile" placeholder="手机号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="员工账号" prop="yuangongzhanghao">
					<el-input v-model="ruleForm.yuangongzhanghao" :readonly="ro.yuangongzhanghao" placeholder="员工账号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="员工姓名" prop="yuangongxingming">
					<el-input v-model="ruleForm.yuangongxingming" :readonly="ro.yuangongxingming" placeholder="员工姓名" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='fuwurenyuan'" label="头像" prop="touxiang">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.touxiang?ruleForm.touxiang:''"
						@change="fuwurenyuantouxiangUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="服务技能" prop="fuwujineng">
					<el-input v-model="ruleForm.fuwujineng" :readonly="ro.fuwujineng" placeholder="服务技能" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='fuwurenyuan'"  label="服务状态" prop="fuwuzhuangtai">
					<el-select filterable v-model="ruleForm.fuwuzhuangtai" :disabled="ro.fuwuzhuangtai" placeholder="请选择服务状态">
						<el-option
							v-for="(item,index) in fuwurenyuanfuwuzhuangtaiOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="收益" prop="shouyi">
					<el-input v-model="ruleForm.shouyi" :readonly="ro.shouyi" placeholder="收益" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='fuwurenyuan'" label="资质证书" prop="zizhizhengshu">
					<file-upload
						tip="点击上传资质证书"
						action="file/upload"
						:limit="1"
						:type="3"
						:multiple="false"
						:fileUrls="ruleForm.zizhizhengshu?ruleForm.zizhizhengshu:''"
						@change="fuwurenyuanzizhizhengshuUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="服务范围" prop="fuwufanwei">
					<el-input v-model="ruleForm.fuwufanwei" :readonly="ro.fuwufanwei" placeholder="服务范围" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='fuwurenyuan'"  label="服务类型" prop="fuwuleixing">
					<el-select filterable v-model="ruleForm.fuwuleixing" :disabled="ro.fuwuleixing" placeholder="请选择服务类型">
						<el-option
							v-for="(item,index) in fuwurenyuanfuwuleixingOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}'   v-if="flag=='fuwurenyuan'"  label="手机号" prop="mobile">
					<el-input v-model="ruleForm.mobile" :readonly="ro.mobile" placeholder="手机号" clearable></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="用户名" prop="username">
					<el-input v-model="ruleForm.username" placeholder="用户名"></el-input>
				</el-form-item>
				<el-form-item :style='{"width":"48%","margin":"0 0 20px 0"}' v-if="flag=='users'" label="头像" prop="image">
					<file-upload
						tip="点击上传头像"
						action="file/upload"
						:limit="1"
						:multiple="false"
						:fileUrls="ruleForm.image?ruleForm.image:''"
						@change="usersimageUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item :style='{"width":"100%","padding":"0","margin":"10px auto 0","justifyContent":"center","display":"flex"}'>
					<el-button class="btn3" type="primary" @click="onUpdateHandler">
						<span class="icon iconfont icon-queren20"></span>
						提交
					</el-button>
				</el-form-item>
		</el-form>
	</div>
</template>
<script>
// 校验引入
	import { 
		isNumber,
		isIntNumer,
		isMobile,
	} from "@/utils/validate";

	export default {
		data() {
			return {
				ruleForm: {},
				ro: {},
				flag: '',
				usersFlag: false,
				yonghuxingbieOptions: [],
				fuwurenyuanfuwuzhuangtaiOptions: [],
				fuwurenyuanfuwuleixingOptions: [],
			};
		},
		mounted() {
			var table = this.$storage.get("sessionTable");
			this.flag = table;
			this.$http({
				url: `${this.$storage.get("sessionTable")}/session`,
				method: "get"
			}).then(({ data }) => {
				if (data && data.code === 0) {
					if(table == 'yonghu') {
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
					if(table == 'fuwurenyuan') {
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

					this.ruleForm = data.data;
				} else {
					this.$message.error(data.msg);
				}
			});
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
		},
		methods: {
			yonghutouxiangUploadChange(fileUrls) {
				this.ruleForm.touxiang = fileUrls;
			},
			fuwurenyuantouxiangUploadChange(fileUrls) {
				this.ruleForm.touxiang = fileUrls;
			},
			fuwurenyuanzizhizhengshuUploadChange(fileUrls) {
				this.ruleForm.zizhizhengshu = fileUrls;
			},
			usersimageUploadChange(fileUrls) {
				this.ruleForm.image = fileUrls;
			},
			onUpdateHandler() {
				if((!this.ruleForm.yonghuzhanghao)&& 'yonghu'==this.flag){
					this.$message.error('用户账号不能为空');
					return
				}
				if((!this.ruleForm.mima)&& 'yonghu'==this.flag){
					this.$message.error('密码不能为空');
					return
				}
				if((!this.ruleForm.yonghuxingming)&& 'yonghu'==this.flag){
					this.$message.error('用户姓名不能为空');
					return
				}
				if(this.ruleForm.touxiang!=null) {
					this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('yonghu' ==this.flag && this.ruleForm.mobile&&(!isMobile(this.ruleForm.mobile))){
					this.$message.error(`手机号应输入手机格式`);
					return
				}
				if('yonghu' ==this.flag && this.ruleForm.status&&(!isIntNumer(this.ruleForm.status))){
					this.$message.error(`状态应输入整数`);
					return
				}
				if((!this.ruleForm.yuangongzhanghao)&& 'fuwurenyuan'==this.flag){
					this.$message.error('员工账号不能为空');
					return
				}
				if((!this.ruleForm.mima)&& 'fuwurenyuan'==this.flag){
					this.$message.error('密码不能为空');
					return
				}
				if((!this.ruleForm.yuangongxingming)&& 'fuwurenyuan'==this.flag){
					this.$message.error('员工姓名不能为空');
					return
				}
				if(this.ruleForm.touxiang!=null) {
					this.ruleForm.touxiang = this.ruleForm.touxiang.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('fuwurenyuan' ==this.flag && this.ruleForm.shouyi&&(!isNumber(this.ruleForm.shouyi))){
					this.$message.error(`收益应输入数字`);
					return
				}
				if(this.ruleForm.zizhizhengshu!=null) {
					this.ruleForm.zizhizhengshu = this.ruleForm.zizhizhengshu.replace(new RegExp(this.$base.url,"g"),"");
				}
				if('fuwurenyuan' ==this.flag && this.ruleForm.mobile&&(!isMobile(this.ruleForm.mobile))){
					this.$message.error(`手机号应输入手机格式`);
					return
				}
				if('fuwurenyuan' ==this.flag && this.ruleForm.status&&(!isIntNumer(this.ruleForm.status))){
					this.$message.error(`状态应输入整数`);
					return
				}
				if('users'==this.flag && this.ruleForm.username.trim().length<1) {
					this.$message.error(`用户名不能为空`);
					return	
				}
				if(this.flag=='users'){
					this.ruleForm.image = this.ruleForm.image.replace(new RegExp(this.$base.url,"g"),"")
				}
				this.$http({
					url: `${this.$storage.get("sessionTable")}/update`,
					method: "post",
					data: this.ruleForm
				}).then(({ data }) => {
					if (data && data.code === 0) {
						if(this.flag=='users'){
							this.$storage.set('headportrait',this.ruleForm.image)
						}else {
							if(this.flag == 'yonghu') {
								this.$storage.set('headportrait',this.ruleForm.touxiang)
							}
							if(this.flag == 'fuwurenyuan') {
								this.$storage.set('headportrait',this.ruleForm.touxiang)
							}
						}
						this.$message({
							message: "修改信息成功",
							type: "success",
							duration: 1500,
							onClose: () => {
								window.location.reload();
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
	.el-date-editor.el-input {
		width: auto;
	}
	
	.add-update-preview .el-form-item ::v-deep .el-form-item__label {
				padding: 0 10px 0 0;
				color: #666;
				white-space: nowrap;
				font-weight: 500;
				width: 180px;
				font-size: 16px;
				line-height: 40px;
				text-align: right;
			}
	
	.add-update-preview .el-form-item ::v-deep .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	
	.add-update-preview .el-input ::v-deep .el-input__inner {
				border: 1px solid #eee;
				border-radius: 4px;
				padding: 0 12px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 40px;
			}
	
	.add-update-preview .el-select ::v-deep .el-input__inner {
				border: 1px solid #eee;
				border-radius: 4px;
				padding: 0 10px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
				height: 40px;
			}
	
	.add-update-preview .el-date-editor ::v-deep .el-input__inner {
				border: 1px solid #eee;
				border-radius: 4px;
				padding: 0 10px 0 30px;
				box-shadow: none;
				outline: none;
				color: #333;
				width: 100%;
				font-size: 16px;
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
	
	.add-update-preview ::v-deep .el-upload-list .el-upload-list__item {
				border: 1px solid #eee;
				cursor: pointer;
				border-radius: 6px;
				color: #999;
				width: 80px;
				font-size: 26px;
				line-height: 80px;
				text-align: center;
				height: 80px;
			}
	
	.add-update-preview ::v-deep .el-upload .el-icon-plus {
				border: 1px solid #eee;
				cursor: pointer;
				border-radius: 6px;
				color: #999;
				width: 80px;
				font-size: 26px;
				line-height: 80px;
				text-align: center;
				height: 80px;
			}
	
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner {
				border: 1px solid #eee;
				border-radius: 4px;
				padding: 12px;
				box-shadow: none;
				outline: none;
				color: #666;
				width: 100%;
				font-size: 16px;
				height: auto;
			}
	
	.add-update-preview .btn3 {
				border: 0;
				cursor: pointer;
				border-radius: 4px;
				padding: 0 24px;
				margin: 4px;
				outline: none;
				color: #fff;
				background: #2dc7a3;
				width: auto;
				font-size: 16px;
				min-width: 110px;
				height: 40px;
				.iconfont {
						margin: 0 2px;
						color: #fff;
						font-size: 16px;
						height: 40px;
					}
	}
	
	.add-update-preview .btn3:hover {
				opacity: 0.8;
			}
	.editor>.avatar-uploader {
		line-height: 0;
		height: 0;
	}
</style>
