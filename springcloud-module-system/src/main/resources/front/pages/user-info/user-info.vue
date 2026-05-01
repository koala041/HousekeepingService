<template>
<view class="content">
	<view class="box" :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}'>
		<view :style='{"width":"100%","padding":"24rpx 48rpx 24rpx 48rpx","background":"#fff","display":"block","height":"auto"}'>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>用户账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuzhanghao"  v-model="ruleForm.yonghuzhanghao" placeholder="用户账号"></input>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>用户姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuxingming"  v-model="ruleForm.yonghuxingming" placeholder="用户姓名"></input>
			</view>
			<view v-if="tableName=='yonghu'" :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">性别</view>
				<picker :style='{"width":"100%","flex":"1","height":"auto"}' :disabled="ro.xingbie" @change="yonghuxingbieChange" :value="yonghuxingbieIndex" :range="yonghuxingbieOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#ffaa32"}' class="uni-input picker-select-input">{{ruleForm.xingbie?ruleForm.xingbie:"请选择性别"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='yonghu'" @tap="yonghutouxiangTap" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>头像</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' v-if="ruleForm.touxiang" style="margin: 0;" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' v-else class="avator" style="margin: 0;" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='yonghu'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>手机号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.mobile"  v-model="ruleForm.mobile" placeholder="手机号"></input>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>员工账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongzhanghao"  v-model="ruleForm.yuangongzhanghao" placeholder="员工账号"></input>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>员工姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongxingming"  v-model="ruleForm.yuangongxingming" placeholder="员工姓名"></input>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" @tap="fuwurenyuantouxiangTap" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>头像</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' v-if="ruleForm.touxiang" style="margin: 0;" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' v-else class="avator" style="margin: 0;" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>服务技能</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwujineng"  v-model="ruleForm.fuwujineng" placeholder="服务技能"></input>
			</view>
			<view v-if="tableName=='fuwurenyuan'" :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务状态</view>
				<picker :style='{"width":"100%","flex":"1","height":"auto"}' :disabled="ro.fuwuzhuangtai" @change="fuwurenyuanfuwuzhuangtaiChange" :value="fuwurenyuanfuwuzhuangtaiIndex" :range="fuwurenyuanfuwuzhuangtaiOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#ffaa32"}' class="uni-input picker-select-input">{{ruleForm.fuwuzhuangtai?ruleForm.fuwuzhuangtai:"请选择服务状态"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>收益</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.shouyi"  v-model="ruleForm.shouyi" placeholder="收益"></input>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" @tap="fuwurenyuanzizhizhengshuTap" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>资质证书</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-if="ruleForm.zizhizhengshu" v-model="ruleForm.zizhizhengshu" placeholder="资质证书"></input>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>服务范围</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwufanwei"  v-model="ruleForm.fuwufanwei" placeholder="服务范围"></input>
			</view>
			<view v-if="tableName=='fuwurenyuan'" :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务类型</view>
				<picker :style='{"width":"100%","flex":"1","height":"auto"}' :disabled="ro.fuwuleixing" @change="fuwurenyuanfuwuleixingChange" :value="fuwurenyuanfuwuleixingIndex" :range="fuwurenyuanfuwuleixingOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#ffaa32"}' class="uni-input picker-select-input">{{ruleForm.fuwuleixing?ruleForm.fuwuleixing:"请选择服务类型"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 20rpx 6rpx 20rpx","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view class="title" :style='{"padding":"0 20rpx 0 0","whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}'>手机号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.mobile"  v-model="ruleForm.mobile" placeholder="手机号"></input>
			</view>
			<view :style='{"padding":"0","margin":"40rpx 0 40rpx 0","flexWrap":"wrap","display":"flex","width":"100%","justifyContent":"center","height":"auto"}' class="btn">
				<button @tap="update()" class="cu-btn lg" :style='{"border":"0","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 20rpx 0","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"28rpx","minWidth":"120rpx","height":"88rpx"}'>提交</button>
				<button @tap="logout()" class="cu-btn lg" :style='{"border":"2rpx solid #dddddd30","padding":"0 20rpx 0 20rpx","margin":"0 10rpx 20rpx 0","color":"#666","borderRadius":"60rpx","background":"#f6f6f6","width":"auto","lineHeight":"88rpx","fontSize":"28rpx","height":"88rpx"}'>退出登录</button>
			</view>
		</view>
	</view>
</view>
</template>

<script>

	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				ruleForm: {
				},
				tableName:"",
				ro: {},
				yonghuxingbieOptions: [],
				yonghuxingbieIndex: 0,
				fuwurenyuanfuwuzhuangtaiOptions: [],
				fuwurenyuanfuwuzhuangtaiIndex: 0,
				fuwurenyuanfuwuleixingOptions: [],
				fuwurenyuanfuwuleixingIndex: 0,
			}
		},
		components: {
			multipleSelect,
			xiaEditor
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			}
		},
		async onLoad() {
			let table = uni.getStorageSync("nowTable");
			let that = this
			let res = {}
			this.tableName = table;
			this.getSession()
			if(this.tableName=='yonghu'){
				// 自定义下拉框值
				this.yonghuxingbieOptions = "男,女".split(',');
				this.yonghuxingbieOptions.forEach((item, index) => {
					if(item==this.ruleForm.xingbie) {
						this.yonghuxingbieIndex = index;
					}
				});
				this.ro = {
					yonghuzhanghao: true,
					mima: false,
					yonghuxingming: false,
					xingbie: false,
					touxiang: false,
					mobile: false,
					status: false,
				}
			}
			if(this.tableName=='fuwurenyuan'){
				// 自定义下拉框值
				this.fuwurenyuanfuwuzhuangtaiOptions = "空闲,工作中".split(',');
				this.fuwurenyuanfuwuzhuangtaiOptions.forEach((item, index) => {
					if(item==this.ruleForm.fuwuzhuangtai) {
						this.fuwurenyuanfuwuzhuangtaiIndex = index;
					}
				});
				// 下拉框
				var fuwuleixingRefParams = {}
				res = await this.$api.option(`fuwuleixing`,`fuwuleixing`,fuwuleixingRefParams);
				this.fuwurenyuanfuwuleixingOptions = res.data;
				this.fuwurenyuanfuwuleixingOptions.unshift("请选择服务类型");
				this.fuwurenyuanfuwuleixingOptions.forEach((item, index) => {
					if(item==this.ruleForm.fuwuleixing) {
						this.fuwurenyuanfuwuleixingIndex = index;
					}
				});
				this.ro = {
					yuangongzhanghao: true,
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
			this.styleChange()
			this.$forceUpdate()
		},
		methods: {
			async getSession(){
				let res = await this.$api.session(this.tableName)
				this.ruleForm = res.data;
				uni.setStorageSync('userSession',JSON.stringify(res.data))
			},
			// 下拉变化
			yonghuxingbieChange(e) {
				this.yonghuxingbieIndex = e.target.value
				this.ruleForm.xingbie = this.yonghuxingbieOptions[this.yonghuxingbieIndex]
			},
			yonghutouxiangTap() {
				if(this.ro.touxiang){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},
			fuwurenyuantouxiangTap() {
				if(this.ro.touxiang){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},
			// 下拉变化
			fuwurenyuanfuwuzhuangtaiChange(e) {
				this.fuwurenyuanfuwuzhuangtaiIndex = e.target.value
				this.ruleForm.fuwuzhuangtai = this.fuwurenyuanfuwuzhuangtaiOptions[this.fuwurenyuanfuwuzhuangtaiIndex]
			},
			fuwurenyuanzizhizhengshuTap() {
				if(this.ro.zizhizhengshu){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.zizhizhengshu = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},
			// 下拉变化
			fuwurenyuanfuwuleixingChange(e) {
				this.fuwurenyuanfuwuleixingIndex = e.target.value
				this.ruleForm.fuwuleixing = this.fuwurenyuanfuwuleixingOptions[this.fuwurenyuanfuwuleixingIndex]
			},

			toggleTab(str,str1) {
				if(this.ro[str1]){
					return false
				}
				this.$refs[str + str1].show();
			},

			styleChange() {
				this.$nextTick(()=>{
					// document.querySelectorAll('. .uni-input-input').forEach(el=>{
					//   el.style.backgroundColor = this.userInfoForm.list.input.backgroundColor
					// })
				})
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			logout() {
				uni.clearStorageSync()
				this.$utils.jump('../login/login');
			},
			// 注册
			async update() {
				if(`yonghu` == this.tableName&&(!this.ruleForm.yonghuzhanghao)){
					this.$utils.msg(`用户账号不能为空`);
					return
				}
				if(`yonghu` == this.tableName&&(!this.ruleForm.mima)){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if(`yonghu` == this.tableName&&(!this.ruleForm.yonghuxingming)){
					this.$utils.msg(`用户姓名不能为空`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				if(`yonghu` == this.tableName && this.ruleForm.status&&(!this.$validate.isIntNumer(this.ruleForm.status))){
					this.$utils.msg(`状态应输入整数`);
					return
				}
				if(`fuwurenyuan` == this.tableName&&(!this.ruleForm.yuangongzhanghao)){
					this.$utils.msg(`员工账号不能为空`);
					return
				}
				if(`fuwurenyuan` == this.tableName&&(!this.ruleForm.mima)){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if(`fuwurenyuan` == this.tableName&&(!this.ruleForm.yuangongxingming)){
					this.$utils.msg(`员工姓名不能为空`);
					return
				}
				if(`fuwurenyuan` == this.tableName && this.ruleForm.shouyi&&(!this.$validate.isNumber(this.ruleForm.shouyi))){
					this.$utils.msg(`收益应输入数字`);
					return
				}
				if(`fuwurenyuan` == this.tableName && this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				if(`fuwurenyuan` == this.tableName && this.ruleForm.status&&(!this.$validate.isIntNumer(this.ruleForm.status))){
					this.$utils.msg(`状态应输入整数`);
					return
				}
				let table = uni.getStorageSync("nowTable");
				await this.$api.update(table, this.ruleForm);
				this.$utils.msgBack('修改成功');;
				this.getSession()
			},
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
</style>
