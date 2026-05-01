<template>
<view class="content">
	<view class="register-box" :style="{'backgroundImage': indexBgUrl?`url(${$base.url + indexBgUrl})`:''}">
		<view :style='{"padding":"0 40rpx","flexWrap":"wrap","display":"flex","width":"100%","position":"relative","justifyContent":"center","height":"auto"}'>
			<image :style='{"width":"180rpx","margin":"0 auto 24rpx auto","borderRadius":"160rpx","display":"none","height":"180rpx"}' :src="indexLogoUrl?($base.url + indexLogoUrl):'/static/service.png'" mode="aspectFill"></image>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">用户账号：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.yonghuzhanghao" v-model="ruleForm.yonghuzhanghao"  type="text"  class="uni-input" name="" placeholder="用户账号" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">密码：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.mima" v-model="ruleForm.mima" type="password"  class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">确认密码：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="ruleForm.mima2" type="password" class="uni-input" name="" placeholder="确认密码" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">用户姓名：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.yonghuxingming" v-model="ruleForm.yonghuxingming"  type="text"  class="uni-input" name="" placeholder="用户姓名" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">性别：</view>
				<picker :style='{"alignItems":"center","width":"100%","display":"flex"}' v-if="tableName=='yonghu'" :disabled="ro.xingbie" @change="yonghuxingbieChange" :value="yonghuxingbieIndex" :range="yonghuxingbieOptions">
					<view>
						<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","flex":"1","borderWidth":"2rpx","background":"#ffffff90","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' class="uni-input">{{ruleForm.xingbie?ruleForm.xingbie:"请选择性别"}}</view>
					</view>
				</picker>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" @tap="yonghutouxiangTap" class="">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">头像：</view>
				<image :style='{"width":"120rpx","margin":"10rpx 0 10rpx 0","borderRadius":"16rpx","objectFit":"cover","display":"block","height":"120rpx"}' v-if="ruleForm.touxiang" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"120rpx","margin":"10rpx 0 10rpx 0","borderRadius":"16rpx","objectFit":"cover","display":"block","height":"120rpx"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">手机：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="ruleForm.mobile" type="text" class="uni-input" name="" placeholder="手机号码" />
			</view>
			<view :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='yonghu' && true" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">验证码：</view>
				<input :style='{"padding":"0 24rpx","margin":"0","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","width":"100%","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="smscode" type="text" class="uni-input" name="" placeholder="请输入短信验证码" />
				<button :style='{"padding":"0","margin":"0 0 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"180rpx","lineHeight":"88rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' type="primary" v-if="isEndFlag" @tap="sendsmscode" class="bg-red">发送验证码</button>
				<button :style='{"padding":"0","margin":"0 0 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"180rpx","lineHeight":"88rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' type="primary" v-if="!isEndFlag" disabled="disabled" class="bg-red">{{SecondToDate}}</button>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">员工账号：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.yuangongzhanghao" v-model="ruleForm.yuangongzhanghao"  type="text"  class="uni-input" name="" placeholder="员工账号" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">密码：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.mima" v-model="ruleForm.mima" type="password"  class="uni-input" name="" placeholder="密码" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">确认密码：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="ruleForm.mima2" type="password" class="uni-input" name="" placeholder="确认密码" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">员工姓名：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.yuangongxingming" v-model="ruleForm.yuangongxingming"  type="text"  class="uni-input" name="" placeholder="员工姓名" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" @tap="fuwurenyuantouxiangTap" class="">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">头像：</view>
				<image :style='{"width":"120rpx","margin":"10rpx 0 10rpx 0","borderRadius":"16rpx","objectFit":"cover","display":"block","height":"120rpx"}' v-if="ruleForm.touxiang" class="avator" :src="baseUrl+ruleForm.touxiang" mode=""></image>
				<image :style='{"width":"120rpx","margin":"10rpx 0 10rpx 0","borderRadius":"16rpx","objectFit":"cover","display":"block","height":"120rpx"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">服务技能：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.fuwujineng" v-model="ruleForm.fuwujineng"  type="text"  class="uni-input" name="" placeholder="服务技能" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" @tap="fuwurenyuanzizhizhengshuTap" class="">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">资质证书：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-if="ruleForm.zizhizhengshu"  v-model="baseUrl+ruleForm.zizhizhengshu" placeholder="资质证书"></input>
				<image :style='{"width":"120rpx","margin":"10rpx 0 10rpx 0","borderRadius":"16rpx","objectFit":"cover","display":"block","height":"120rpx"}' v-else class="avator" src="../../static/gen/upload.png" mode=""></image>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">服务范围：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' :disabled="ro.fuwufanwei" v-model="ruleForm.fuwufanwei"  type="text"  class="uni-input" name="" placeholder="服务范围" />
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">服务类型：</view>
				<picker :style='{"alignItems":"center","width":"100%","display":"flex"}' v-if="tableName=='fuwurenyuan'" :disabled="ro.fuwuleixing" @change="fuwurenyuanfuwuleixingChange" :value="fuwurenyuanfuwuleixingIndex" :range="fuwurenyuanfuwuleixingOptions">
					<view>
						<view :style='{"padding":"0 20rpx 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","flex":"1","borderWidth":"2rpx","background":"#ffffff90","fontSize":"28rpx","lineHeight":"88rpx","borderStyle":"solid","height":"88rpx"}' class="uni-input">{{ruleForm.fuwuleixing?ruleForm.fuwuleixing:"请选择服务类型"}}</view>
					</view>
				</picker>
			</view>
			<view :style='{"border":"0","padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan'" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">手机：</view>
				<input :style='{"padding":"0px 24rpx","margin":"0px","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="ruleForm.mobile" type="text" class="uni-input" name="" placeholder="手机号码" />
			</view>
			<view :style='{"padding":"0 10rpx 0 10rpx","margin":"0 0 24rpx 0","borderRadius":"60rpx","background":"none","display":"flex","width":"100%","height":"auto"}' v-if="tableName=='fuwurenyuan' && true" class="uni-form-item uni-column">
				<view :style='{"whiteSpace":"nowrap","color":"#333","textAlign":"right","width":"auto","fontSize":"28rpx","lineHeight":"80rpx","minWidth":"144rpx","height":"88rpx"}' class="label">验证码：</view>
				<input :style='{"padding":"0 24rpx","margin":"0","borderColor":"#ff6f9f50","color":"#333","borderRadius":"40rpx","flex":"1","background":"#ffffff90","borderWidth":"2rpx","width":"100%","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' v-model="smscode" type="text" class="uni-input" name="" placeholder="请输入短信验证码" />
				<button :style='{"padding":"0","margin":"0 0 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"180rpx","lineHeight":"88rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' type="primary" v-if="isEndFlag" @tap="sendsmscode" class="bg-red">发送验证码</button>
				<button :style='{"padding":"0","margin":"0 0 0 20rpx","borderColor":"#ff6f9f50","color":"#ff6f9f","borderRadius":"40rpx","background":"#ffffff90","borderWidth":"2rpx","width":"180rpx","lineHeight":"88rpx","fontSize":"28rpx","borderStyle":"solid","height":"88rpx"}' type="primary" v-if="!isEndFlag" disabled="disabled" class="bg-red">{{SecondToDate}}</button>
			</view>
			<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"24rpx 0 24rpx 0","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"32rpx","minWidth":"300rpx","height":"88rpx"}' class="btn-submit" @tap="register" type="primary">注册</button>
			
			<view class="idea1" :style='{"margin":" 0 0 40rpx 0","textAlign":"center","background":"none","display":"block","width":"100%","fontSize":"52rpx","height":"80rpx","order":"-1"}'>欢迎注册</view>
			<view class="idea2" :style='{"width":"100%","background":"#ffaa33","display":"none","height":"80rpx"}'>idea2</view>
			<view class="idea3" :style='{"width":"100%","background":"#ffaa33","display":"none","height":"80rpx"}'>idea3</view>
		</view>
	</view>
</view>
</template>

<script>
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				yonghuxingbieOptions: [],
				yonghuxingbieIndex: 0,
				fuwurenyuanfuwuzhuangtaiOptions: [],
				fuwurenyuanfuwuzhuangtaiIndex: 0,
				fuwurenyuanfuwuleixingOptions: [],
				fuwurenyuanfuwuleixingIndex: 0,
				ruleForm: {
				yonghuzhanghao: '',
				mima: '',
				yonghuxingming: '',
				xingbie: '',
				touxiang: '',
				mobile: '',
				status: '',
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
				},
				ro: {},
				smscode: "",
				// 倒计时定时器
				inter: null,
				// 倒计时
				count: 10,
				//倒计时结束标识
				isEndFlag: true,
				tableName:"",
				indexBgUrl: '',
				indexLogoUrl: '',
			}
		},
		components: {
			multipleSelect
		},
		computed: {
			baseUrl() {
				return this.$base.url;
			},
			SecondToDate: function() {
				var time = this.count;
				time = parseInt(time) + "秒后重发";
				return time;
			}
		},
		async onLoad() {
			let res = [];
			let table = uni.getStorageSync("loginTable");
			this.tableName = table;

			if(this.tableName=='yonghu'){
				// 自定义下拉框值
				this.yonghuxingbieOptions = "男,女".split(',');
				this.ruleForm.xingbie=this.yonghuxingbieOptions[0]
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
				// 自定义下拉框值
				this.fuwurenyuanfuwuzhuangtaiOptions = "空闲,工作中".split(',');
				this.ruleForm.fuwuzhuangtai=this.fuwurenyuanfuwuzhuangtaiOptions[0]
                this.ruleForm.fuwuzhuangtai='空闲'
                this.ruleForm.shouyi='0'
				var fuwuleixingRefParams = {}
				res = await this.$api.option(`fuwuleixing`,`fuwuleixing`,fuwuleixingRefParams);
				this.fuwurenyuanfuwuleixingOptions = res.data;
				this.fuwurenyuanfuwuleixingOptions.unshift("请选择服务类型");
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
			
			this.styleChange()
		},
		methods: {
			// 发送邮箱验证码
			async sendsmscode(){
				if(!this.ruleForm.mobile){
					this.$utils.msg(`手机号码不能为空`);
					return
				}
				if(this.ruleForm.mobile&&(!this.$validate.isMobile(this.ruleForm.mobile))){
					this.$utils.msg(`请输入正常的手机格式`);
					return
				}
				if (this.count > 0) {
					this.isEndFlag = false;
					var _this = this;
					this.inter = setInterval(function() {
						_this.count = _this.count - 1;
						if (_this.count <= 0) {
							clearInterval(_this.inter);
							_this.count=60;
							_this.isEndFlag = true;
						}
					}, 1000);
				}
				let res = await this.$api.sendsms(`${this.tableName}`, this.ruleForm.mobile);
				if (res.code == 0) {
					this.$utils.msg(`发送成功`);
				} else {
					this.$utils.errMsg(`服务器异常，请稍后重试`);
					return;
				}
			},

			// 下拉变化
			yonghuxingbieChange(e) {
					this.yonghuxingbieIndex = e.target.value
					this.ruleForm.xingbie = this.yonghuxingbieOptions[this.yonghuxingbieIndex]
			},
			yonghutouxiangTap() {
				if(this.ro.touxiang) {
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.touxiang = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},
			fuwurenyuantouxiangTap() {
				if(this.ro.touxiang) {
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
				if(this.ro.zizhizhengshu) {
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

			async styleChange() {
				let rs = await this.$api.getPublic('config/info?name=appRegisterBackgroudImg')
				this.indexBgUrl = rs.data?rs.data.value:''
				rs = await this.$api.getPublic('config/info?name=appRegLogo')
				this.indexLogoUrl = rs.data?rs.data.value:''
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 注册
			async register() {
				
				if((!this.ruleForm.yonghuzhanghao) && `yonghu` == this.tableName){
					this.$utils.msg(`用户账号不能为空`);
					return
				}
				if((!this.ruleForm.mima) && `yonghu` == this.tableName){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if(`yonghu` == this.tableName && (this.ruleForm.mima!=this.ruleForm.mima2)){
					this.$utils.msg(`两次密码输入不一致`);
					return
				}
				if((!this.ruleForm.yonghuxingming) && `yonghu` == this.tableName){
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
				if((!this.ruleForm.yuangongzhanghao) && `fuwurenyuan` == this.tableName){
					this.$utils.msg(`员工账号不能为空`);
					return
				}
				if((!this.ruleForm.mima) && `fuwurenyuan` == this.tableName){
					this.$utils.msg(`密码不能为空`);
					return
				}
				if(`fuwurenyuan` == this.tableName && (this.ruleForm.mima!=this.ruleForm.mima2)){
					this.$utils.msg(`两次密码输入不一致`);
					return
				}
				if((!this.ruleForm.yuangongxingming) && `fuwurenyuan` == this.tableName){
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
				await this.$api.registerSms(`${this.tableName}`, this.ruleForm, this.smscode);
				this.$utils.msgBack('注册成功');;
			}
		}
	}
</script>

<style lang="scss" scoped>
	.content {
		min-height: calc(100vh - 44px);
		box-sizing: border-box;
	}
	.register-box{
		padding: 160rpx 24rpx 24rpx 24rpx;
		background: radial-gradient(circle at 20% 12%, #fff 0, #fff 90rpx, transparent 220rpx), linear-gradient(180deg, #fff1f5 0%, #ffe6ee 58%, #fff9f6 100%);
		width: 100%;
		min-height: 100%;
		height: auto;
	}
</style>
