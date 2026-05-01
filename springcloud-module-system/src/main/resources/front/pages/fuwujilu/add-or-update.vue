  

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
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongzhanghao" v-model="ruleForm.yuangongzhanghao" placeholder="员工账号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongxingming" v-model="ruleForm.yuangongxingming" placeholder="员工姓名"  type="text"></input>
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
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">用户账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuzhanghao" v-model="ruleForm.yonghuzhanghao" placeholder="用户账号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">用户姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yonghuxingming" v-model="ruleForm.yonghuxingming" placeholder="用户姓名"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">手机号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.shoujihao" v-model="ruleForm.shoujihao" placeholder="手机号"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务时间</view>
				<input :disabled="ro.fuwushijian" :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.fuwushijian" placeholder="服务时间" @tap="toggleTab('fuwushijian')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="fuwutupianTap">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务图片</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.fuwutupian" :src="baseUrl+ruleForm.fuwutupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
              
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务记录</view>
				<textarea :style='{"border":"0","padding":"24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"240rpx"}' v-model="ruleForm.fuwujilu" placeholder="服务记录" :maxlength="-1" :disabled="ro.fuwujilu"></textarea>
			</view>
			
			<view :style='{"width":"100%","padding":"0","margin":"20rpx 0 40rpx 0","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 30rpx 30rpx 30rpx","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"30rpx","minWidth":"220rpx","height":"88rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="fuwushijianConfirm" ref="fuwushijian" themeColor="#333333"></w-picker>
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
				yuangongzhanghao: '',
				yuangongxingming: '',
				fuwuleixing: '',
				fuwufeiyong: '',
				yonghuzhanghao: '',
				yonghuxingming: '',
				shoujihao: '',
				fuwushijian: '',
				fuwuqueren: '待确认' ,
				fuwutupian: '',
				fuwujilu: '',
				},
				fuwuquerenOptions: [],
				fuwuquerenIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   yuyuebianhao : false,
				   xiangmubianhao : false,
				   xiangmumingcheng : false,
				   yuangongzhanghao : false,
				   yuangongxingming : false,
				   fuwuleixing : false,
				   fuwufeiyong : false,
				   yonghuzhanghao : false,
				   yonghuxingming : false,
				   shoujihao : false,
				   fuwushijian : false,
				   fuwuqueren : false,
				   fuwutupian : false,
				   fuwujilu : false,
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
			this.ruleForm.fuwushijian =  this.$utils.getCurDateTime();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取
			this.ruleForm.yuangongzhanghao = this.user.yuangongzhanghao
			this.ro.yuangongzhanghao = true;
			this.ruleForm.yuangongxingming = this.user.yuangongxingming
			this.ro.yuangongxingming = true;

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
					if(o=='yonghuzhanghao'){
						this.ruleForm.yonghuzhanghao = obj[o];
						this.ro.yonghuzhanghao = true;
						continue;
					}
					if(o=='yonghuxingming'){
						this.ruleForm.yonghuxingming = obj[o];
						this.ro.yonghuxingming = true;
						continue;
					}
					if(o=='shoujihao'){
						this.ruleForm.shoujihao = obj[o];
						this.ro.shoujihao = true;
						continue;
					}
					if(o=='fuwushijian'){
						this.ruleForm.fuwushijian = obj[o];
						this.ro.fuwushijian = true;
						continue;
					}
					if(o=='fuwuqueren'){
						this.ruleForm.fuwuqueren = obj[o];
						for(let x in this.fuwuquerenOptions) {
							if(this.fuwuquerenOptions[x] == this.ruleForm.fuwuqueren) {
								this.fuwuquerenIndex = Number(x)
							}
						}
						this.ro.fuwuqueren = true;
						continue;
					}
					if(o=='fuwutupian'){
						this.ruleForm.fuwutupian = obj[o].split(",")[0];
						this.ro.fuwutupian = true;
						continue;
					}
					if(o=='fuwujilu'){
						this.ruleForm.fuwujilu = obj[o];
						this.ro.fuwujilu = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.fuwuquerenOptions = "已确认,待确认".split(',')
			this.fuwuquerenOptions.unshift("请选择服务确认");
			if(this.ruleForm.fuwuqueren) {
				for(let x in this.fuwuquerenOptions) {
					if(this.fuwuquerenOptions[x] == this.ruleForm.fuwuqueren) {
						this.fuwuquerenIndex = Number(x)
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
				res = await this.$api.info(`fuwujilu`, this.ruleForm.id);
				if(res.data.fuwuqueren) {
					for(let x in this.fuwuquerenOptions) {
						if(this.fuwuquerenOptions[x] == res.data.fuwuqueren) {
							this.fuwuquerenIndex = Number(x)
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
			fuwushijianConfirm(val) {
				this.ruleForm.fuwushijian = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			fuwuquerenChange(e) {
				this.fuwuquerenIndex = e.target.value
				this.ruleForm.fuwuqueren = this.fuwuquerenOptions[this.fuwuquerenIndex]
			},

			fuwutupianTap() {
				if(this.ro.fuwutupian){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.fuwutupian = 'upload/' + res.file;
					_this.$forceUpdate();
				});
			},

			getUUID () {
				return new Date().getTime();
			},
			async onSubmitTap(subMitType=null) {
				let that = this
				//跨表计算判断
				var obj;
				if((!this.ruleForm.xiangmumingcheng)){
					this.$utils.msg(`项目名称不能为空`);
					return
				}
				if(this.ruleForm.fuwufeiyong&&(!this.$validate.isNumber(this.ruleForm.fuwufeiyong))){
					this.$utils.msg(`服务费用应输入数字`);
					return
				}
				if(this.ruleForm.shoujihao&&(!this.$validate.isMobile(this.ruleForm.shoujihao))){
					this.$utils.msg(`手机号应输入手机格式`);
					return
				}
				if((!this.ruleForm.fuwutupian)){
					this.$utils.msg(`服务图片不能为空`);
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
							var notUpdateColumn = 'fuwuzhuangtai,fuwuzhuangtai'.split(',')
							if(changeInList(statusColumnName,notUpdateColumn)) {
								for (var o in obj){
									if(o==statusColumnName){
										obj[o] = statusColumnValue;
									}
								}
								var table = uni.getStorageSync('crossTable');
								await this.$api.update(`${table}`, obj);
							}
							function changeInList(name,arr){
								for(let x in arr) {
									if(arr[x] == name) {
										return false
									}
								}
								return true
							}
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
						let res = await this.$api.list(`fuwujilu`, params);
						if (res.data.total >= crossoptnum) {
							this.$utils.msg(uni.getStorageSync('tips'));
							uni.removeStorageSync('crossCleanType');
							return false;
						}
					}
					//跨表计算
				}
				if(this.cross) {
					var ott = await this.$api.postPublic('updateColumn/fuwurenyuan/1', {
						csuUpdateColumn: 'fuwuzhuangtai',
						csuUpdateColumnValue: '空闲',
						csuConditionColumn: 'yuangongzhanghao',
						csuConditionColumnValue: this.ruleForm.yuangongzhanghao
					})
					if(ott.code!=0) {
						that.$utils.msg('更新失败');
						return false
					}
				}
				if(this.cross) {
					var ott = await this.$api.postPublic('updateColumn/fuwudingdan/1', {
						csuUpdateColumn: 'fuwuzhuangtai',
						csuUpdateColumnValue: '已服务',
						csuConditionColumn: 'yuyuebianhao',
						csuConditionColumnValue: this.ruleForm.yuyuebianhao
					})
					if(ott.code!=0) {
						that.$utils.msg('更新失败');
						return false
					}
				}
				let oet = {}
				if(this.ruleForm.id){
					await this.$api.update(`fuwujilu`, this.ruleForm);
				}else{
					oet = await this.$api.add(`fuwujilu`, this.ruleForm);
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
