  

<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}' v-if="showType==1">
		<form :style='{"width":"100%","padding":"24rpx 48rpx 24rpx 48rpx","background":"#fff","display":"block","height":"auto"}' class="app-update-pv">
























			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">预约编号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuyuebianhao" v-model="ruleForm.yuyuebianhao" placeholder="预约编号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目编号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xiangmubianhao" v-model="ruleForm.xiangmubianhao" placeholder="项目编号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目名称</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.xiangmumingcheng" v-model="ruleForm.xiangmumingcheng" placeholder="项目名称"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="xiangmutupianTap">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目图片</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.xiangmutupian" :src="baseUrl+ruleForm.xiangmutupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务类型</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwuleixing" v-model="ruleForm.fuwuleixing" placeholder="服务类型"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务费用</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwufeiyong" v-model="ruleForm.fuwufeiyong" placeholder="服务费用" type="digit" @input="(e)=>numberChange(e,'fuwufeiyong')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">佣金占比</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yongjinzhanbi" v-model="ruleForm.yongjinzhanbi" placeholder="佣金占比" type="digit" @input="(e)=>numberChange(e,'yongjinzhanbi')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">收益</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' disabled v-model="shouyi" placeholder="收益"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongzhanghao" v-model="ruleForm.yuangongzhanghao" placeholder="员工账号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongxingming" v-model="ruleForm.yuangongxingming" placeholder="员工姓名"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">结算日期</view>
				<input :disabled="ro.jiesuanriqi" :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.jiesuanriqi" placeholder="结算日期" @tap="toggleTab('jiesuanriqi')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">提现状态</view>
				<picker :disabled="ro.tixianzhuangtai" :style='{"width":"100%","flex":"1","height":"auto"}' @change="tixianzhuangtaiChange" :value="tixianzhuangtaiIndex" :range="tixianzhuangtaiOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#666"}' class="uni-input">{{ruleForm.tixianzhuangtai?ruleForm.tixianzhuangtai:"请选择提现状态"}}</view>
				</picker>
			</view>
            
			
			<view :style='{"width":"100%","padding":"0","margin":"20rpx 0 40rpx 0","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 30rpx 30rpx 30rpx","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"30rpx","minWidth":"220rpx","height":"88rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="jiesuanriqiConfirm" ref="jiesuanriqi" themeColor="#333333"></w-picker>
	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				yuyuebianhao: '',
				xiangmubianhao: '',
				xiangmumingcheng: '',
				xiangmutupian: '',
				fuwuleixing: '',
				fuwufeiyong: '',
				yongjinzhanbi: '',
				shouyi: '',
				yuangongzhanghao: '',
				yuangongxingming: '',
				jiesuanriqi: '',
				tixianzhuangtai: '已结算' ,
				},
				tixianzhuangtaiOptions: [],
				tixianzhuangtaiIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   yuyuebianhao : false,
				   xiangmubianhao : false,
				   xiangmumingcheng : false,
				   xiangmutupian : false,
				   fuwuleixing : false,
				   fuwufeiyong : false,
				   yongjinzhanbi : false,
				   shouyi : false,
				   yuangongzhanghao : false,
				   yuangongxingming : false,
				   jiesuanriqi : false,
				   tixianzhuangtai : false,
				},
				virtualPay: false,
				showType: 1,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
		},
		computed: {
			shouyi : {
				get: function () {
					let c = this.ruleForm
					let a = c.fuwufeiyong*c.yongjinzhanbi
					this.ruleForm.shouyi = a?Number(Number(a).toFixed(2)):0
					return a?Number(Number(a).toFixed(2)):0
				}
				
			},
			baseUrl() {
				return this.$base.url;
			},
			sessionForm() {
				return uni.getStorageSync("appUserid")?JSON.parse(uni.getStorageSync('userSession')):{}
			},



		},
		async onLoad(options) {
			if(options.virtualPay){
				this.virtualPay = true
			}
			this.ruleForm.jiesuanriqi =  this.$utils.getCurDateTime();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取

			this.ro.tixianzhuangtai = true;
			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
					if(o=='yuyuebianhao'){
						this.ruleForm.yuyuebianhao = obj[o];
						this.ro.yuyuebianhao = true;
						continue;
					}
					if(o=='xiangmubianhao'){
						this.ruleForm.xiangmubianhao = obj[o];
						this.ro.xiangmubianhao = true;
						continue;
					}
					if(o=='xiangmumingcheng'){
						this.ruleForm.xiangmumingcheng = obj[o];
						this.ro.xiangmumingcheng = true;
						continue;
					}
					if(o=='xiangmutupian'){
						this.ruleForm.xiangmutupian = obj[o].split(",")[0];
						this.ro.xiangmutupian = true;
						continue;
					}
					if(o=='fuwuleixing'){
						this.ruleForm.fuwuleixing = obj[o];
						this.ro.fuwuleixing = true;
						continue;
					}
					if(o=='fuwufeiyong'){
						this.ruleForm.fuwufeiyong = obj[o];
						this.ro.fuwufeiyong = true;
						continue;
					}
					if(o=='yongjinzhanbi'){
						this.ruleForm.yongjinzhanbi = obj[o];
						this.ro.yongjinzhanbi = true;
						continue;
					}
					if(o=='shouyi'){
						this.ruleForm.shouyi = obj[o];
						this.ro.shouyi = true;
						continue;
					}
					if(o=='yuangongzhanghao'){
						this.ruleForm.yuangongzhanghao = obj[o];
						this.ro.yuangongzhanghao = true;
						continue;
					}
					if(o=='yuangongxingming'){
						this.ruleForm.yuangongxingming = obj[o];
						this.ro.yuangongxingming = true;
						continue;
					}
					if(o=='jiesuanriqi'){
						this.ruleForm.jiesuanriqi = obj[o];
						this.ro.jiesuanriqi = true;
						continue;
					}
					if(o=='tixianzhuangtai'){
						this.ruleForm.tixianzhuangtai = obj[o];
						for(let x in this.tixianzhuangtaiOptions) {
							if(this.tixianzhuangtaiOptions[x] == this.ruleForm.tixianzhuangtai) {
								this.tixianzhuangtaiIndex = Number(x)
							}
						}
						this.ro.tixianzhuangtai = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.tixianzhuangtaiOptions = "已提现,已结算".split(',')
			this.tixianzhuangtaiOptions.unshift("请选择提现状态");
			if(this.ruleForm.tixianzhuangtai) {
				for(let x in this.tixianzhuangtaiOptions) {
					if(this.tixianzhuangtaiOptions[x] == this.ruleForm.tixianzhuangtai) {
						this.tixianzhuangtaiIndex = Number(x)
					}
				}
			}

			// 如果有登录，获取登录后保存的userid
			this.ruleForm.userid = uni.getStorageSync("appUserid")
			if (options.refid) {
				// 如果上一级页面传递了refid，获取改refid数据信息
				this.ruleForm.refid = Number(options.refid);
				this.ruleForm.nickname = uni.getStorageSync("nickname");
			}
			// 如果是更新操作
			if (options.id) {
				this.ruleForm.id = options.id;
				// 获取信息
				res = await this.$api.info(`caiwujiesuan`, this.ruleForm.id);
				if(res.data.tixianzhuangtai) {
					for(let x in this.tixianzhuangtaiOptions) {
						if(this.tixianzhuangtaiOptions[x] == res.data.tixianzhuangtai) {
							this.tixianzhuangtaiIndex = Number(x)
						}
					}
				}
				this.ruleForm = res.data;
			}
			this.$forceUpdate()
			if (uni.getStorageSync('raffleType') && uni.getStorageSync('raffleType') != null) {
				uni.removeStorageSync('raffleType')
				setTimeout(() => {
					this.onSubmitTap(null)
				}, 300)
			}
		},
		methods: {
			numberChange(e) {
				e = Number(e)
			},

			// 多级联动参数


			// 日长控件选择日期时间
			jiesuanriqiConfirm(val) {
				this.ruleForm.jiesuanriqi = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			tixianzhuangtaiChange(e) {
				this.tixianzhuangtaiIndex = e.target.value
				this.ruleForm.tixianzhuangtai = this.tixianzhuangtaiOptions[this.tixianzhuangtaiIndex]
			},

			xiangmutupianTap() {
				if(this.ro.xiangmutupian){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.xiangmutupian = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap(subMitType=null) {
				let that = this
				if(this.ruleForm.shouyi==0){
					this.$utils.msg('收益不能为空')
					return false
				}
				//跨表计算判断
				var obj;
				if((!this.ruleForm.xiangmubianhao)){
					this.$utils.msg(`项目编号不能为空`);
					return
				}
				if((!this.ruleForm.xiangmumingcheng)){
					this.$utils.msg(`项目名称不能为空`);
					return
				}
				if(this.ruleForm.fuwufeiyong&&(!this.$validate.isNumber(this.ruleForm.fuwufeiyong))){
					this.$utils.msg(`服务费用应输入数字`);
					return
				}
				if(this.ruleForm.yongjinzhanbi&&(!this.$validate.isNumber(this.ruleForm.yongjinzhanbi))){
					this.$utils.msg(`佣金占比应输入数字`);
					return
				}
				if(this.ruleForm.shouyi&&(!this.$validate.isNumber(this.ruleForm.shouyi))){
					this.$utils.msg(`收益应输入数字`);
					return
				}
				//更新跨表属性
				var crossuserid;
				var crossrefid;
				var crossoptnum;
				if(this.cross){
					var statusColumnName = uni.getStorageSync('statusColumnName');
					var statusColumnValue = uni.getStorageSync('statusColumnValue');
					if(statusColumnName!='') {
						if(!obj) {
							obj = uni.getStorageSync('crossObj');
						}
						if(!statusColumnName.startsWith("[")) {
							for (var o in obj){
								if(o==statusColumnName){
									obj[o] = statusColumnValue;
								}
							}
							var table = uni.getStorageSync('crossTable');
							await this.$api.update(`${table}`, obj);
						} else {
								crossuserid=Number(uni.getStorageSync('appUserid'));
								crossrefid=obj['id'];
								crossoptnum=uni.getStorageSync('statusColumnName');
								crossoptnum=crossoptnum.replace(/\[/,"").replace(/\]/,"");
						}
					}
					if(crossrefid && crossuserid) {
						this.ruleForm.crossuserid=crossuserid;
						this.ruleForm.crossrefid=crossrefid;
						let params = {
							page: 1,
							limit:10,
							crossuserid:crossuserid,
							crossrefid:crossrefid,
						}
						let res = await this.$api.list(`caiwujiesuan`, params);
						if (res.data.total >= crossoptnum) {
							this.$utils.msg(uni.getStorageSync('tips'));
							uni.removeStorageSync('crossCleanType');
							return false;
						}
					}
					//跨表计算
				}
				let oet = {}
				if(this.ruleForm.id){
					await this.$api.update(`caiwujiesuan`, this.ruleForm);
				}else{
					oet = await this.$api.add(`caiwujiesuan`, this.ruleForm);
				}
				if(this.cross){
					uni.setStorageSync('crossCleanType',true);
				}
				that.$utils.msgBack('提交成功');
			},
			optionsChange(e) {
				this.index = e.target.value
			},
			bindDateChange(e) {
				this.date = e.target.value
			},
			getDate(type) {
				const date = new Date();
				let year = date.getFullYear();
				let month = date.getMonth() + 1;
				let day = date.getDate();
				if (type === 'start') {
					year = year - 60;
				} else if (type === 'end') {
					year = year + 2;
				}
				month = month > 9 ? month : '0' + month;;
				day = day > 9 ? day : '0' + day;
				return `${year}-${month}-${day}`;
			},
			toggleTab(str) {
				if(this.ro[str]){
					return false
				}
				this.$refs[str].show();
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
