  

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
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务类型</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwuleixing" v-model="ruleForm.fuwuleixing" placeholder="服务类型"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务费用</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwufeiyong" v-model="ruleForm.fuwufeiyong" placeholder="服务费用" type="digit" @input="(e)=>numberChange(e,'fuwufeiyong')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">完成时间</view>
				<input :disabled="ro.wanchengshijian" :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' v-model="ruleForm.wanchengshijian" placeholder="完成时间" @tap="toggleTab('wanchengshijian')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="" @tap="fankuitupianTap">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">反馈图片</view>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-if="ruleForm.fankuitupian" :src="baseUrl+ruleForm.fankuitupian.split(',')[0]" mode="aspectFill"></image>
				<image :style='{"width":"80rpx","borderRadius":"80rpx","objectFit":"cover","display":"block","height":"80rpx"}' class="avator" v-else src="../../static/gen/upload.png" mode="aspectFill"></image>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">评分</view>
				<picker :disabled="ro.pingfen" :style='{"width":"100%","flex":"1","height":"auto"}' @change="pingfenChange" :value="pingfenIndex" :range="pingfenOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#666"}' class="uni-input">{{ruleForm.pingfen?ruleForm.pingfen:"请选择评分"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工姓名</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongxingming" v-model="ruleForm.yuangongxingming" placeholder="员工姓名"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">员工账号</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yuangongzhanghao" v-model="ruleForm.yuangongzhanghao" placeholder="员工账号"  type="text"></input>
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
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务评价</view>
				<textarea :style='{"border":"0","padding":"24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"240rpx"}' v-model="ruleForm.fuwupingjia" placeholder="服务评价" :maxlength="-1" :disabled="ro.fuwupingjia"></textarea>
			</view>
			
			<view :style='{"width":"100%","padding":"0","margin":"20rpx 0 40rpx 0","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 30rpx 30rpx 30rpx","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"30rpx","minWidth":"220rpx","height":"88rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker :starttime="wanchengshijianstarttime" mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="wanchengshijianConfirm" ref="wanchengshijian" themeColor="#333333"></w-picker>
	</view>
	<view v-else-if="showType==2">
		<view style="width: 100%;display: flex;flex-wrap: wrap;padding: 10px;">
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuqueren','服务评分统计'))||(!userid&&isAuthFront('fuwuqueren','服务评分统计'))" size="mini" type="primary" @click="chartClick1()">
				服务评分统计
			</button>
		</view>
		<view style="width: 100%;">
			<view v-if="chartType1" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				服务评分统计
			</view>
			<qiun-data-charts
				v-if="chartType1"
				style="width: 100%;height:"
				type="line"
				:opts="opts1"
				:chartData="chartData1">
			</qiun-data-charts>
		</view>
	</view>
</view>
</template>

<script>
	import wPicker from "@/components/w-picker/w-picker.vue";
	import xiaEditor from '@/components/xia-editor/xia-editor';
	import multipleSelect from "@/components/momo-multipleSelect/momo-multipleSelect";
	import qiunDataCharts from '@/components/qiun-data-charts/components/qiun-data-charts/qiun-data-charts.vue';
	export default {
		data() {
			return {
				cross:'',
				ruleForm: {
				yuyuebianhao: '',
				xiangmubianhao: '',
				xiangmumingcheng: '',
				fuwuleixing: '',
				fuwufeiyong: '',
				wanchengshijian: '',
				fankuitupian: '',
				pingfen: '',
				fuwupingjia: '',
				yuangongxingming: '',
				yuangongzhanghao: '',
				yonghuzhanghao: '',
				yonghuxingming: '',
				jiesuanzhuangtai: '结算中' ,
				shhf: '',
				},
				wanchengshijianstarttime: '',
				pingfenOptions: [],
				pingfenIndex: 0,
				jiesuanzhuangtaiOptions: [],
				jiesuanzhuangtaiIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   yuyuebianhao : false,
				   xiangmubianhao : false,
				   xiangmumingcheng : false,
				   fuwuleixing : false,
				   fuwufeiyong : false,
				   wanchengshijian : false,
				   fankuitupian : false,
				   pingfen : false,
				   fuwupingjia : false,
				   yuangongxingming : false,
				   yuangongzhanghao : false,
				   yonghuzhanghao : false,
				   yonghuxingming : false,
				   jiesuanzhuangtai : false,
				   shhf : false,
				},
				virtualPay: false,
				showType: 1,
				userid: '',
				chartData1: {},
				opts1: {},
				chartType1: false,
			}
		},
		components: {
			wPicker,
			xiaEditor,
			multipleSelect,
			qiunDataCharts,
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
			if(options.showType) {
				this.showType = 2
				if(options.userid&&options.userid!='') {
					this.userid = options.userid
				}
				return false
			}else {
				this.showType = 1
			}
			if(options.virtualPay){
				this.virtualPay = true
			}
			this.wanchengshijianstarttime = this.$utils.getCurDate()
			this.ruleForm.wanchengshijian =  this.$utils.getCurDateTime();
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取
			this.ruleForm.yonghuzhanghao = this.user.yonghuzhanghao
			this.ro.yonghuzhanghao = true;
			this.ruleForm.yonghuxingming = this.user.yonghuxingming
			this.ro.yonghuxingming = true;

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
					if(o=='wanchengshijian'){
						this.ruleForm.wanchengshijian = obj[o];
						this.ro.wanchengshijian = true;
						continue;
					}
					if(o=='fankuitupian'){
						this.ruleForm.fankuitupian = obj[o].split(",")[0];
						this.ro.fankuitupian = true;
						continue;
					}
					if(o=='pingfen'){
						this.ruleForm.pingfen = obj[o];
						for(let x in this.pingfenOptions) {
							if(this.pingfenOptions[x] == this.ruleForm.pingfen) {
								this.pingfenIndex = Number(x)
							}
						}
						this.ro.pingfen = true;
						continue;
					}
					if(o=='fuwupingjia'){
						this.ruleForm.fuwupingjia = obj[o];
						this.ro.fuwupingjia = true;
						continue;
					}
					if(o=='yuangongxingming'){
						this.ruleForm.yuangongxingming = obj[o];
						this.ro.yuangongxingming = true;
						continue;
					}
					if(o=='yuangongzhanghao'){
						this.ruleForm.yuangongzhanghao = obj[o];
						this.ro.yuangongzhanghao = true;
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
					if(o=='jiesuanzhuangtai'){
						this.ruleForm.jiesuanzhuangtai = obj[o];
						for(let x in this.jiesuanzhuangtaiOptions) {
							if(this.jiesuanzhuangtaiOptions[x] == this.ruleForm.jiesuanzhuangtai) {
								this.jiesuanzhuangtaiIndex = Number(x)
							}
						}
						this.ro.jiesuanzhuangtai = true;
						continue;
					}
				}
			}

			// 自定义下拉框值
			this.pingfenOptions = "1,2,3,4,5,6,7,8,9,10".split(',')
			this.pingfenOptions.unshift("请选择评分");
			if(this.ruleForm.pingfen) {
				for(let x in this.pingfenOptions) {
					if(this.pingfenOptions[x] == this.ruleForm.pingfen) {
						this.pingfenIndex = Number(x)
					}
				}
			}
			// 自定义下拉框值
			this.jiesuanzhuangtaiOptions = "已结算,结算中".split(',')
			this.jiesuanzhuangtaiOptions.unshift("请选择结算状态");
			if(this.ruleForm.jiesuanzhuangtai) {
				for(let x in this.jiesuanzhuangtaiOptions) {
					if(this.jiesuanzhuangtaiOptions[x] == this.ruleForm.jiesuanzhuangtai) {
						this.jiesuanzhuangtaiIndex = Number(x)
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
				res = await this.$api.info(`fuwuqueren`, this.ruleForm.id);
				if(res.data.pingfen) {
					for(let x in this.pingfenOptions) {
						if(this.pingfenOptions[x] == res.data.pingfen) {
							this.pingfenIndex = Number(x)
						}
					}
				}
				if(res.data.jiesuanzhuangtai) {
					for(let x in this.jiesuanzhuangtaiOptions) {
						if(this.jiesuanzhuangtaiOptions[x] == res.data.jiesuanzhuangtai) {
							this.jiesuanzhuangtaiIndex = Number(x)
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
			wanchengshijianConfirm(val) {
				this.ruleForm.wanchengshijian = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			pingfenChange(e) {
				this.pingfenIndex = e.target.value
				this.ruleForm.pingfen = this.pingfenOptions[this.pingfenIndex]
			},
			// 下拉变化
			jiesuanzhuangtaiChange(e) {
				this.jiesuanzhuangtaiIndex = e.target.value
				this.ruleForm.jiesuanzhuangtai = this.jiesuanzhuangtaiOptions[this.jiesuanzhuangtaiIndex]
			},

			fankuitupianTap() {
				if(this.ro.fankuitupian){
					return false
				}
				let _this = this;
				this.$api.upload(function(res) {
					_this.ruleForm.fankuitupian = 'upload/' + res.file;
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
				if((!this.ruleForm.yuyuebianhao)){
					this.$utils.msg(`预约编号不能为空`);
					return
				}
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
				if((!this.ruleForm.wanchengshijian)){
					this.$utils.msg(`完成时间不能为空`);
					return
				}
				if((!this.ruleForm.fankuitupian)){
					this.$utils.msg(`反馈图片不能为空`);
					return
				}
				if(this.ruleForm.pingfen&&(!this.$validate.isIntNumer(this.ruleForm.pingfen))){
					this.$utils.msg(`评分应输入整数`);
					return
				}
				if((!this.ruleForm.fuwupingjia)){
					this.$utils.msg(`服务评价不能为空`);
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
						let res = await this.$api.list(`fuwuqueren`, params);
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
					await this.$api.update(`fuwuqueren`, this.ruleForm);
				}else{
					oet = await this.$api.add(`fuwuqueren`, this.ruleForm);
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
			async chartClick1() {
				let params = {}
				let res = await this.$api.getPublic('fuwuqueren/value/yuangongxingming/pingfen', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['yuangongxingming']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['yuangongxingming']
					})
				}
				this.opts1 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,10,0,15],
					enableScroll: false,
					legend: {
						show: false
					},
					xAxis: {
						disableGrid: true
					},
					yAxis: {
						gridType: "dash",
						dashLength: 2,
						showTitle: false
					},
					extra: {
						line: {
							type: "straight",
							width: 2,
							activeType: "hollow"
						}
					}
				}
				this.chartData1 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType1 = true
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
