  

<template>
<view class="content">
	<view :style='{"minHeight":"100%","width":"100%","padding":"0","position":"relative","background":"#fff","height":"auto"}' v-if="showType==1">
		<form :style='{"width":"100%","padding":"24rpx 48rpx 24rpx 48rpx","background":"#fff","display":"block","height":"auto"}' class="app-update-pv">




































			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">项目编号</view>
				<view :style='{"padding":"0 24rpx 0 24rpx","margin":"0","lineHeight":"80rpx","fontSize":"28rpx","color":"#333","flex":"1"}' class="right-input">
					{{ruleForm.xiangmubianhao}}
				</view>
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
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class=" select">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务类型</view>
				<picker :disabled="ro.fuwuleixing" :style='{"width":"100%","flex":"1","height":"auto"}' @change="fuwuleixingChange" :value="fuwuleixingIndex" :range="fuwuleixingOptions">
					<view :style='{"width":"100%","lineHeight":"80rpx","fontSize":"28rpx","color":"#666"}' class="uni-input">{{ruleForm.fuwuleixing?ruleForm.fuwuleixing:"请选择服务类型"}}</view>
				</picker>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务费用</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwufeiyong" v-model="ruleForm.fuwufeiyong" placeholder="服务费用" type="digit" @input="(e)=>numberChange(e,'fuwufeiyong')"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务内容</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwuneirong" v-model="ruleForm.fuwuneirong" placeholder="服务内容"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">服务说明</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.fuwushuoming" v-model="ruleForm.fuwushuoming" placeholder="服务说明"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">营业时间</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.yingyeshijian" v-model="ruleForm.yingyeshijian" placeholder="营业时间"  type="text"></input>
			</view>
			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">联系电话</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' :disabled="ro.lianxidianhua" v-model="ruleForm.lianxidianhua" placeholder="联系电话"  type="text"></input>
			</view>
       			<view :style='{"padding":"6rpx 0 6rpx 0","margin":"0 0 24rpx 0","borderColor":"#25252530","alignItems":"center","borderRadius":"0","borderWidth":"0 0 2rpx 0","display":"flex","width":"100%","borderStyle":"solid","height":"auto"}' class="">
				<view :style='{"padding":"0 20rpx 0 20rpx","whiteSpace":"nowrap","color":"#000","textAlign":"right","width":"auto","lineHeight":"80rpx","fontSize":"28rpx"}' class="title">位置</view>
				<input :style='{"border":"0","padding":"0 24rpx 0 24rpx","margin":"0px","color":"#333","borderRadius":"8rpx","flex":"1","background":"none","fontSize":"28rpx","height":"80rpx"}' readonly @tap="fulladdressTap('weizhi')" v-model="ruleForm.weizhi2" placeholder="位置" :maxlength="-1"></input>
			</view>
            
			
			<view :style='{"width":"100%","padding":"0","margin":"20rpx 0 40rpx 0","height":"auto"}' class="btn" >
				<button :style='{"border":"0","padding":"0 40rpx 0 40rpx","margin":"0 30rpx 30rpx 30rpx","color":"#fff","borderRadius":"60rpx","background":"#ff6f9f","width":"auto","lineHeight":"88rpx","fontSize":"30rpx","minWidth":"220rpx","height":"88rpx"}' @tap="onSubmitTap(null)" class="bg-red">提交</button>
			</view>
		</form>
		<w-picker  mode="dateTime" step="1" :current="false" :hasSecond="false" @confirm="clicktimeConfirm" ref="clicktime" themeColor="#333333"></w-picker>
	</view>
	<view v-else-if="showType==2">
		<view style="width: 100%;display: flex;flex-wrap: wrap;padding: 10px;">
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuxinxi','服务类型占比'))||(!userid&&isAuthFront('fuwuxinxi','服务类型占比'))" size="mini" type="primary" @click="chartClick1()">
				服务类型占比
			</button>
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuxinxi','服务项目收藏量'))||(!userid&&isAuthFront('fuwuxinxi','服务项目收藏量'))" size="mini" type="primary" @click="chartClick2()">
				服务项目收藏量
			</button>
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuxinxi','服务项目评分值统计'))||(!userid&&isAuthFront('fuwuxinxi','服务项目评分值统计'))" size="mini" type="primary" @click="chartClick3()">
				服务项目评分值统计
			</button>
			<button style="margin: 0 10px 0 0" v-if="(userid&&isAuth('fuwuxinxi','服务项目点赞量统计'))||(!userid&&isAuthFront('fuwuxinxi','服务项目点赞量统计'))" size="mini" type="primary" @click="chartClick4()">
				服务项目点赞量统计
			</button>
		</view>
		<view style="width: 100%;">
			<view v-if="chartType1" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				服务类型占比
			</view>
			<qiun-data-charts
				v-if="chartType1"
				style="width: 100%;height:"
				type="rose"
				:opts="opts1"
				:chartData="chartData1">
			</qiun-data-charts>
			<view v-if="chartType2" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				服务项目收藏量
			</view>
			<qiun-data-charts
				v-if="chartType2"
				style="width: 100%;height:"
				type="column"
				:opts="opts2"
				:chartData="chartData2">
			</qiun-data-charts>
			<view v-if="chartType3" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				服务项目评分值统计
			</view>
			<qiun-data-charts
				v-if="chartType3"
				style="width: 100%;height:"
				type="line"
				:opts="opts3"
				:chartData="chartData3">
			</qiun-data-charts>
			<view v-if="chartType4" style="width: 100%;text-align: center;line-height: 2;font-size: 12px;color: #000">
				服务项目点赞量统计
			</view>
			<qiun-data-charts
				v-if="chartType4"
				style="width: 100%;height:"
				type="area"
				:opts="opts4"
				:chartData="chartData4">
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
				xiangmubianhao: this.getUUID(),
				xiangmumingcheng: '',
				xiangmutupian: '',
				fuwuleixing: '',
				fuwufeiyong: '',
				fuwuneirong: '',
				fuwushuoming: '',
				weizhi: '',
				yingyeshijian: '8:00-24:00' ,
				lianxidianhua: '',
				onshelves: Number('1') ,
				xiaoliang: Number('0') ,
				discussnum: '',
				totalscore: '',
				storeupnum: '',
				},
				fuwuleixingOptions: [],
				fuwuleixingIndex: 0,
				// 登录用户信息
				user: {},
				ro:{
				   xiangmubianhao : false,
				   xiangmumingcheng : false,
				   xiangmutupian : false,
				   fuwuleixing : false,
				   fuwufeiyong : false,
				   fuwuneirong : false,
				   fuwushuoming : false,
				   weizhi : false,
				   yingyeshijian : false,
				   lianxidianhua : false,
				   onshelves : false,
				   xiaoliang : false,
				   thumbsupnum : false,
				   crazilynum : false,
				   clicktime : false,
				   discussnum : false,
				   totalscore : false,
				   storeupnum : false,
				},
				virtualPay: false,
				showType: 1,
				userid: '',
				chartData1: {},
				opts1: {},
				chartType1: false,
				chartData2: {},
				opts2: {},
				chartType2: false,
				chartData3: {},
				opts3: {},
				chartType3: false,
				chartData4: {},
				opts4: {},
				chartType4: false,
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
			let table = uni.getStorageSync("nowTable");
			// 获取用户信息
			let res = await this.$api.session(table);
			this.user = res.data;
			
			// ss读取

			this.ro.xiaoliang = true;
			// 跨表
			this.cross = options.cross;
			if(options.cross){
				var obj = uni.getStorageSync('crossObj');
				for (var o in obj){
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
						for(let x in this.fuwuleixingOptions) {
							if(this.fuwuleixingOptions[x] == this.ruleForm.fuwuleixing) {
								this.fuwuleixingIndex = Number(x)
							}
						}
						this.ro.fuwuleixing = true;
						continue;
					}
					if(o=='fuwufeiyong'){
						this.ruleForm.fuwufeiyong = obj[o];
						this.ro.fuwufeiyong = true;
						continue;
					}
					if(o=='fuwuneirong'){
						this.ruleForm.fuwuneirong = obj[o];
						this.ro.fuwuneirong = true;
						continue;
					}
					if(o=='fuwushuoming'){
						this.ruleForm.fuwushuoming = obj[o];
						this.ro.fuwushuoming = true;
						continue;
					}
					if(o=='weizhi'){
						this.ruleForm.weizhi2 = obj[o].split(';')[2]
						this.ruleForm.weizhi = obj[o];
						this.ro.weizhi = true;
						continue;
					}
					if(o=='yingyeshijian'){
						this.ruleForm.yingyeshijian = obj[o];
						this.ro.yingyeshijian = true;
						continue;
					}
					if(o=='lianxidianhua'){
						this.ruleForm.lianxidianhua = obj[o];
						this.ro.lianxidianhua = true;
						continue;
					}
					if(o=='onshelves'){
						this.ruleForm.onshelves = obj[o];
						this.ro.onshelves = true;
						continue;
					}
					if(o=='xiaoliang'){
						this.ruleForm.xiaoliang = obj[o];
						this.ro.xiaoliang = true;
						continue;
					}
					if(o=='thumbsupnum'){
						this.ruleForm.thumbsupnum = obj[o];
						this.ro.thumbsupnum = true;
						continue;
					}
					if(o=='crazilynum'){
						this.ruleForm.crazilynum = obj[o];
						this.ro.crazilynum = true;
						continue;
					}
					if(o=='clicktime'){
						this.ruleForm.clicktime = obj[o];
						this.ro.clicktime = true;
						continue;
					}
					if(o=='discussnum'){
						this.ruleForm.discussnum = obj[o];
						this.ro.discussnum = true;
						continue;
					}
					if(o=='totalscore'){
						this.ruleForm.totalscore = obj[o];
						this.ro.totalscore = true;
						continue;
					}
					if(o=='storeupnum'){
						this.ruleForm.storeupnum = obj[o];
						this.ro.storeupnum = true;
						continue;
					}
				}
			}

			var fuwuleixingRefParams = {}
			// 下拉框
			res = await this.$api.option(`fuwuleixing`,`fuwuleixing`,fuwuleixingRefParams);
			this.fuwuleixingOptions = res.data;
			this.fuwuleixingOptions.unshift("请选择服务类型");
			if(this.ruleForm.fuwuleixing) {
				for(let x in this.fuwuleixingOptions) {
					if(this.fuwuleixingOptions[x] == this.ruleForm.fuwuleixing) {
						this.fuwuleixingIndex = Number(x)
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
				res = await this.$api.info(`fuwuxinxi`, this.ruleForm.id);
				if(res.data.fuwuleixing) {
					for(let x in this.fuwuleixingOptions) {
						if(this.fuwuleixingOptions[x] == res.data.fuwuleixing) {
							this.fuwuleixingIndex = Number(x)
						}
					}
				}
				res.data.weizhi2 = res.data.weizhi.split(';')[2]
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
			fulladdressTap (name='') {
				if(this.ro[name]) {
					return false
				}
				uni.chooseLocation({
					success: (data) => {
						if(name){
							this.ruleForm[name] = data.longitude + ';' + data.latitude + ';' + data.address
							this.ruleForm[name + 2] = data.address
						}else {
							this.ruleForm.fulladdress = data.address;
							this.ruleForm.longitude = data.longitude;
							this.ruleForm.latitude = data.latitude;
						}
						this.$forceUpdate()
					}
				})
			},

			// 多级联动参数


			// 日长控件选择日期时间
			clicktimeConfirm(val) {
				this.ruleForm.clicktime = val.result;
				this.$forceUpdate();
			},

			// 下拉变化
			fuwuleixingChange(e) {
				this.fuwuleixingIndex = e.target.value
				this.ruleForm.fuwuleixing = this.fuwuleixingOptions[this.fuwuleixingIndex]
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
				if(this.ruleForm.xiangmubianhao){
					this.ruleForm.xiangmubianhao = String(this.ruleForm.xiangmubianhao)
				}
				//跨表计算判断
				var obj;
				if(this.ruleForm.fuwufeiyong&&(!this.$validate.isNumber(this.ruleForm.fuwufeiyong))){
					this.$utils.msg(`服务费用应输入数字`);
					return
				}
				if(this.ruleForm.lianxidianhua&&(!this.$validate.isMobile(this.ruleForm.lianxidianhua))){
					this.$utils.msg(`联系电话应输入手机格式`);
					return
				}
				if(this.ruleForm.onshelves&&(!this.$validate.isIntNumer(this.ruleForm.onshelves))){
					this.$utils.msg(`上下架应输入整数`);
					return
				}
				if(this.ruleForm.xiaoliang&&(!this.$validate.isIntNumer(this.ruleForm.xiaoliang))){
					this.$utils.msg(`销量应输入整数`);
					return
				}
				if(this.ruleForm.thumbsupnum&&(!this.$validate.isIntNumer(this.ruleForm.thumbsupnum))){
					this.$utils.msg(`赞应输入整数`);
					return
				}
				if(this.ruleForm.crazilynum&&(!this.$validate.isIntNumer(this.ruleForm.crazilynum))){
					this.$utils.msg(`踩应输入整数`);
					return
				}
				if(this.ruleForm.discussnum&&(!this.$validate.isIntNumer(this.ruleForm.discussnum))){
					this.$utils.msg(`评论数应输入整数`);
					return
				}
				if(this.ruleForm.totalscore&&(!this.$validate.isNumber(this.ruleForm.totalscore))){
					this.$utils.msg(`评分应输入数字`);
					return
				}
				if(this.ruleForm.storeupnum&&(!this.$validate.isIntNumer(this.ruleForm.storeupnum))){
					this.$utils.msg(`收藏数应输入整数`);
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
						let res = await this.$api.list(`fuwuxinxi`, params);
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
					await this.$api.update(`fuwuxinxi`, this.ruleForm);
				}else{
					oet = await this.$api.add(`fuwuxinxi`, this.ruleForm);
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
				let res = await this.$api.getPublic('fuwuxinxi/group/fuwuleixing', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['fuwuleixing']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['fuwuleixing']
					})
				}
				this.opts1 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [5,5,5,5],
					enableScroll: false,
					legend: {
						show: true,
						position: "left",
						lineHeight: 25
					},
					extra: {
						rose: {
							type: "area",
							minRadius: 50,
							activeOpacity: 0.5,
							activeRadius: 10,
							offsetAngle: 0,
							labelWidth: 15,
							border: false,
							borderWidth: 2,
							borderColor: "#FFFFFF"
						}
					}
				}
				this.chartData1 = {
					series: [
						{
							data: pArray
						}
					]
				};
				this.chartType1 = true
			},
			async chartClick2() {
				let params = {}
				let res = await this.$api.getPublic('fuwuxinxi/value/xiangmumingcheng/storeupnum', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['xiangmumingcheng']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['xiangmumingcheng']
					})
				}
				this.opts2 = {
					color: ["#1890FF","#91CB74","#FAC858","#EE6666","#73C0DE","#3CA272","#FC8452","#9A60B4","#ea7ccc"],
					padding: [15,15,10,5],
					enableScroll: false,
					xAxis: {
						disableGrid: true,
						rotateLabel: true,
						rotateAngle: 30
					},
					legend: {
						show: false
					},
					yAxis: {},
					extra: {
						column: {
							type: "group",
							width: 30,
							activeBgColor: "#000000",
							activeBgOpacity: 0.08
						}
					}
				}
				this.chartData2 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType2 = true
			},
			async chartClick3() {
				let params = {}
				let res = await this.$api.getPublic('fuwuxinxi/value/xiangmumingcheng/totalscore', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['xiangmumingcheng']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['xiangmumingcheng']
					})
				}
				this.opts3 = {
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
				this.chartData3 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType3 = true
			},
			async chartClick4() {
				let params = {}
				let res = await this.$api.getPublic('fuwuxinxi/value/xiangmumingcheng/thumbsupnum', params)
				res = res.data
				let xAxis = [];
				let yAxis = [];
				let pArray = []
				for(let i=0;i<res.length;i++){
					xAxis.push(res[i]['xiangmumingcheng']);
					yAxis.push(parseFloat((res[i].total)));
					pArray.push({
						value: parseFloat((res[i].total)),
						name: res[i]['xiangmumingcheng']
					})
				}
				this.opts4 = {
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
						area: {
							type: "curve",
							opacity: 0.2,
							addLine: true,
							width: 2,
							gradient: true,
							activeType: "hollow"
						}
					}
				}
				this.chartData4 = {
					categories: xAxis,
					series: [
						{
							data: yAxis,
							name: ''
						}
					]
				};
				this.chartType4 = true
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
