


































<template>
	<div class="addEdit-block">
		<el-form
			class="add-update-preview"
			ref="ruleForm"
			:model="ruleForm"
			:rules="rules"
			label-width="180px"
		>
			<template >
				<el-form-item class="input" v-if="type!='info'" label="预约编号" prop="yuyuebianhao" >
					<el-input v-model="ruleForm.yuyuebianhao" placeholder="预约编号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.yuyuebianhao" label="预约编号" prop="yuyuebianhao" >
					<el-input v-model="ruleForm.yuyuebianhao" placeholder="预约编号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="项目编号" prop="xiangmubianhao" >
					<el-input v-model="ruleForm.xiangmubianhao" placeholder="项目编号" clearable  :readonly="ro.xiangmubianhao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="项目编号" prop="xiangmubianhao" >
					<el-input v-model="ruleForm.xiangmubianhao" placeholder="项目编号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="项目名称" prop="xiangmumingcheng" >
					<el-input v-model="ruleForm.xiangmumingcheng" placeholder="项目名称" clearable  :readonly="ro.xiangmumingcheng"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="项目名称" prop="xiangmumingcheng" >
					<el-input v-model="ruleForm.xiangmumingcheng" placeholder="项目名称" readonly></el-input>
				</el-form-item>
				<el-form-item class="upload" v-if="type!='info' && !ro.xiangmutupian" label="项目图片" prop="xiangmutupian" >
					<file-upload
						tip="点击上传项目图片"
						action="file/upload"
						:limit="3"
						:disabled="ro.xiangmutupian"
						:multiple="true"
						:fileUrls="ruleForm.xiangmutupian?ruleForm.xiangmutupian:''"
						@change="xiangmutupianUploadChange"
					></file-upload>
				</el-form-item>
				<el-form-item class="upload" v-else-if="ruleForm.xiangmutupian" label="项目图片" prop="xiangmutupian" >
					<img v-if="ruleForm.xiangmutupian.substring(0,4)=='http'&&ruleForm.xiangmutupian.split(',w').length>1" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.xiangmutupian" width="100" height="100" @click="imgPreView(ruleForm.tupian)">
					<img v-else-if="ruleForm.xiangmutupian.substring(0,4)=='http'" class="upload-img" style="margin-right:20px;" v-bind:key="index" :src="ruleForm.xiangmutupian.split(',')[0]" width="100" height="100" @click="imgPreView(ruleForm.tupian.split(',')[0])">
					<img v-else class="upload-img" style="margin-right:20px;" v-bind:key="index" v-for="(item,index) in ruleForm.xiangmutupian.split(',')" :src="$base.url+item" width="100" height="100" @click="imgPreView($base.url+item)">
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="服务类型" prop="fuwuleixing" >
					<el-input v-model="ruleForm.fuwuleixing" placeholder="服务类型" clearable  :readonly="ro.fuwuleixing"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="服务类型" prop="fuwuleixing" >
					<el-input v-model="ruleForm.fuwuleixing" placeholder="服务类型" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="服务费用" prop="fuwufeiyong" >
					<el-input-number v-model="ruleForm.fuwufeiyong" placeholder="服务费用" :disabled="ro.fuwufeiyong"></el-input-number>
				</el-form-item>
				<el-form-item v-else class="input" label="服务费用" prop="fuwufeiyong" >
					<el-input v-model="ruleForm.fuwufeiyong" placeholder="服务费用" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="服务内容" prop="fuwuneirong" >
					<el-input v-model="ruleForm.fuwuneirong" placeholder="服务内容" clearable  :readonly="ro.fuwuneirong"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="服务内容" prop="fuwuneirong" >
					<el-input v-model="ruleForm.fuwuneirong" placeholder="服务内容" readonly></el-input>
				</el-form-item>
				<el-form-item class="date" v-if="type!='info'" label="预约日期" prop="yuyueriqi" >
					<el-date-picker
						format="yyyy 年 MM 月 dd 日"
						value-format="yyyy-MM-dd"
						v-model="ruleForm.yuyueriqi" 
						type="date"
						:readonly="ro.yuyueriqi"
						:picker-options="yuyueriqiPickerOptions"
						placeholder="预约日期"
					></el-date-picker> 
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.yuyueriqi" label="预约日期" prop="yuyueriqi" >
					<el-input v-model="ruleForm.yuyueriqi" placeholder="预约日期" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="预约时段" prop="yuyueshiduan" >
					<el-select :disabled="ro.yuyueshiduan" v-model="ruleForm.yuyueshiduan" placeholder="请选择预约时段"  filterable>
						<el-option
							v-for="(item,index) in yuyueshiduanOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="预约时段" prop="yuyueshiduan" >
					<el-input v-model="ruleForm.yuyueshiduan"
						placeholder="预约时段" readonly></el-input>
				</el-form-item>
				<el-form-item class="date" v-if="type!='info'" label="预约时间" prop="yuyueshijian" >
					<el-date-picker
						value-format="yyyy-MM-dd HH:mm:ss"
						v-model="ruleForm.yuyueshijian" 
						type="datetime"
						:readonly="ro.yuyueshijian"
						placeholder="预约时间"
					></el-date-picker>
				</el-form-item>
				<el-form-item class="input" v-else-if="ruleForm.yuyueshijian" label="预约时间" prop="yuyueshijian" >
					<el-input v-model="ruleForm.yuyueshijian" placeholder="预约时间" readonly></el-input>
				</el-form-item>
				<el-form-item class="select" v-if="type!='info'"  label="订单状态" prop="dingdanzhuangtai" >
					<el-select :disabled="ro.dingdanzhuangtai" v-model="ruleForm.dingdanzhuangtai" placeholder="请选择订单状态"  filterable>
						<el-option
							v-for="(item,index) in dingdanzhuangtaiOptions"
							v-bind:key="index"
							:label="item"
							:value="item">
						</el-option>
					</el-select>
				</el-form-item>
				<el-form-item v-else class="input" label="订单状态" prop="dingdanzhuangtai" >
					<el-input v-model="ruleForm.dingdanzhuangtai"
						placeholder="订单状态" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="用户账号" prop="yonghuzhanghao" >
					<el-input v-model="ruleForm.yonghuzhanghao" placeholder="用户账号" clearable  :readonly="ro.yonghuzhanghao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="用户账号" prop="yonghuzhanghao" >
					<el-input v-model="ruleForm.yonghuzhanghao" placeholder="用户账号" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="用户姓名" prop="yonghuxingming" >
					<el-input v-model="ruleForm.yonghuxingming" placeholder="用户姓名" clearable  :readonly="ro.yonghuxingming"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="用户姓名" prop="yonghuxingming" >
					<el-input v-model="ruleForm.yonghuxingming" placeholder="用户姓名" readonly></el-input>
				</el-form-item>
				<el-form-item class="input" v-if="type!='info'"  label="手机号" prop="shoujihao" >
					<el-input v-model="ruleForm.shoujihao" placeholder="手机号" clearable  :readonly="ro.shoujihao"></el-input>
				</el-form-item>
				<el-form-item v-else class="input" label="手机号" prop="shoujihao" >
					<el-input v-model="ruleForm.shoujihao" placeholder="手机号" readonly></el-input>
				</el-form-item>
			</template>
			<el-form-item class="input" v-if="type!='info'"  label="位置" prop="weizhi" >
				<el-input @click.native="openMapDiolag('weizhi')"  v-model="ruleForm.weizhi2" placeholder="请选择位置"></el-input>
			</el-form-item>
			<el-form-item class="input" v-else-if="ruleForm.weizhi" label="位置" prop="weizhi" >
				<el-input v-model="ruleForm.weizhi2" placeholder="请选择位置" readonly></el-input>
			</el-form-item>
			<el-form-item class="input" v-if="type!='info'"  label="服务地址" prop="fuwudizhi" >
				<el-input @click.native="openMapDiolag('fuwudizhi')"  v-model="ruleForm.fuwudizhi2" placeholder="请选择服务地址"></el-input>
			</el-form-item>
			<el-form-item class="input" v-else-if="ruleForm.fuwudizhi" label="服务地址" prop="fuwudizhi" >
				<el-input v-model="ruleForm.fuwudizhi2" placeholder="请选择服务地址" readonly></el-input>
			</el-form-item>
			<el-form-item class="btn">
				<el-button class="btn3"  v-if="type!='info'" type="success" @click="onSubmit">
					<span class="icon iconfont icon-queren20"></span>
					提交
				</el-button>
				<el-button class="btn4" v-if="type!='info'" type="success" @click="back()">
					<span class="icon iconfont icon-guanbi15"></span>
					取消
				</el-button>
				<el-button class="btn5" v-if="type=='info'" type="success" @click="back()">
					<span class="icon iconfont icon-fanhui14"></span>
					返回
				</el-button>
			</el-form-item>
		</el-form>
    
		<el-dialog
			width="50%"
			title="坐标查询"
			:visible.sync="mapVisiable"
			append-to-body>
			<el-amap-search-box class="search-box" :on-search-result="onSearchResult" :search-option="searchOption" ></el-amap-search-box>
			<div class="amap-wrapper">
				<el-amap class="amap-box" :vid="'amap-vue'" 
				:center="center"
				:zoom="zoom"
				:plugin="plugin"
				:events="events" >
				<el-amap-marker v-bind:key="marker" v-for="marker in markers" :position="marker" :events="events"></el-amap-marker>
				</el-amap>
			</div>
			<div>坐标：[{{ refColumn?(ruleForm[refColumn]?ruleForm[refColumn].split(';')[0]:''):ruleForm.longitude }}, {{ refColumn?(ruleForm[refColumn]?ruleForm[refColumn].split(';')[1]:''):ruleForm.latitude }}],地址：{{refColumn?ruleForm[refColumn + '2']:ruleForm.fulladdress}}</div>
		</el-dialog>

	</div>
</template>
<script>
	import { 
		isNumber,
		isMobile,
	} from "@/utils/validate";
	import Vue from 'vue'
	import VueAMap from 'vue-amap'
	export default {
		data() {
			let self = this
			var validateMobile = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isMobile(value)) {
					callback(new Error("请输入正确的手机号码"));
				} else {
					callback();
				}
			};
			var validateNumber = (rule, value, callback) => {
				if(!value){
					callback();
				} else if (!isNumber(value)) {
					callback(new Error("请输入数字"));
				} else {
					callback();
				}
			};
			return {
				id: '',
				type: '',
			
			
				ro:{
					yuyuebianhao : false,
					xiangmubianhao : false,
					xiangmumingcheng : false,
					xiangmutupian : false,
					fuwuleixing : false,
					fuwufeiyong : false,
					weizhi : false,
					fuwuneirong : false,
					yuyueriqi : false,
					yuyueshiduan : false,
					yuyueshijian : false,
					dingdanzhuangtai : false,
					yonghuzhanghao : false,
					yonghuxingming : false,
					fuwudizhi : false,
					shoujihao : false,
					ispay : false,
				},
				refColumn: '',
				// 地图
				mapVisiable: false,
				zoom: 12,
				center: [116.410426, 39.934946],
				markers: [],
				searchOption: {
					citylimit: false
				},
				plugin: [{
					// 定位城市
					pName: "CitySearch",
					events: {
						init(o) {
							// o是高德地图定位插件实例
							o.getLocalCity(function(status, result) {
								if (status === "complete" && result.info === "OK") {
									// 查询成功，result即为当前所在城市信息
									self.center = [result.rectangle.split(';')[0].split(',')[0], result.rectangle.split(';')[0].split(',')[1]];
								}
							});
						}
					}
				}, ],
				events: {
					click (e) {
						let { lng, lat } = e.lnglat
						if(self.refColumn){
							
						}else {
							self.ruleForm.longitude = lng
							self.ruleForm.latitude = lat
						}
						
						// 这里通过高德 SDK 完成。
						/* eslint-disable */
						var geocoder = new AMap.Geocoder({
							radius: 1000,
							extensions: 'all'
						})
						geocoder.getAddress([lng, lat], function (status, result) {
							if (status === 'complete' && result.info === 'OK') {
								if (result && result.regeocode) {
									self.mapVisiable = false
									if(self.refColumn){
										self.ruleForm[self.refColumn] = lng + ';' + lat + ';' + result.regeocode.formattedAddress
										self.ruleForm[self.refColumn + '2'] = result.regeocode.formattedAddress
									}else {
										self.ruleForm.fulladdress = result.regeocode.formattedAddress
									}
									
								}
							}
						})
					}
				},
			
				ruleForm: {
					yuyuebianhao: this.getUUID(),
					xiangmubianhao: '',
					xiangmumingcheng: '',
					xiangmutupian: '',
					fuwuleixing: '',
					fuwufeiyong: '',
					weizhi: '',
					fuwuneirong: '',
					yuyueriqi: '',
					yuyueshiduan: '',
					yuyueshijian: '',
					dingdanzhuangtai: '预约中',
					yonghuzhanghao: '',
					yonghuxingming: '',
					fuwudizhi: '',
					shoujihao: '',
					ispay: '未支付',
				},
				yuyueshiduanOptions: [],
				dingdanzhuangtaiOptions: [],

				yuyueriqiPickerOptions: {
					disabledDate(time) {
						return time.getTime() < Date.now() - 8.64e7;
					}
				},
				rules: {
					yuyuebianhao: [
						{ required: true, message: '预约编号不能为空', trigger: 'blur' },
					],
					xiangmubianhao: [
						{ required: true, message: '项目编号不能为空', trigger: 'blur' },
					],
					xiangmumingcheng: [
						{ required: true, message: '项目名称不能为空', trigger: 'blur' },
					],
					xiangmutupian: [
					],
					fuwuleixing: [
					],
					fuwufeiyong: [
						{ validator: validateNumber, trigger: 'blur' },
					],
					weizhi: [
					],
					fuwuneirong: [
					],
					yuyueriqi: [
						{ required: true, message: '预约日期不能为空', trigger: 'blur' },
					],
					yuyueshiduan: [
						{ required: true, message: '预约时段不能为空', trigger: 'blur' },
					],
					yuyueshijian: [
					],
					dingdanzhuangtai: [
					],
					yonghuzhanghao: [
					],
					yonghuxingming: [
					],
					fuwudizhi: [
					],
					shoujihao: [
						{ validator: validateMobile, trigger: 'blur' },
					],
					ispay: [
					],
				},
			};
		},
		props: ["parent"],
		computed: {
			sessionForm() {
				return JSON.parse(this.$storage.getObj('userForm'))
			},
			sessionTable() {
				return this.$storage.get('sessionTable')
			},



		},
		components: {
		},
		created() {
			this.ruleForm.yuyueshijian = this.getCurDateTime()
		},
		methods: {
			imgPreView(url){
				this.$parent.imgPreView(url)
			},
			// 打开坐标定位窗口
			async openMapDiolag (value=null) {
				if(window._AMapSecurityConfig==undefined) {
					await this.$http.get('config/info?name=gaode').then(rs=>{
						if(rs.data.data&&rs.data.data.value) {
							let res = JSON.parse(rs.data.data.value)
							window._AMapSecurityConfig = {
								securityJsCode: res.code,
							};
							VueAMap.initAMapApiLoader({
								key: res.key,
								plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor', 'AMap.Geocoder','AMap.CitySearch'],
								// 默认高德 sdk 版本为 1.4.4
								v: '1.4.4'
							})
							Vue.use(VueAMap)
							this.$nextTick(()=>{
								if(value){
									this.refColumn = value
								}else{
									this.refColumn = ''
								}
								this.mapVisiable = true
							})
						}
						
					})
				}else {
					if(value){
						this.refColumn = value
					}else{
						this.refColumn = ''
					}
					this.mapVisiable = true
				}
			},
			// 查询
			onSearchResult (pois) {
				let latSum = 0;
				let lngSum = 0;
				if (pois.length > 0) {
					pois.forEach(poi => {
						let {lng, lat} = poi;
						lngSum += lng;
						latSum += lat;
						this.markers.push([poi.lng, poi.lat]);
					});
					let center = {
						lng: lngSum / pois.length,
						lat: latSum / pois.length
					};
					this.center = [center.lng, center.lat];
				}
			},
			// 下载
			download(file){
				window.open(`${file}`)
			},
			// 初始化
			init(id,type ) {
				if (id) {
					this.id = id;
					this.type = type;
				}
				if(this.type=='info'||this.type=='else'||this.type=='msg'){
					this.info(id);
				}else if(this.type=='logistics'){
					for(let x in this.ro) {
						this.ro[x] = true
					}
					this.logistics=false;
					this.info(id);
				}else if(this.type=='cross'){
					var obj = this.$storage.getObj('crossObj');
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
							this.ruleForm.xiangmutupian = obj[o];
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
						if(o=='weizhi'){
							this.ruleForm.weizhi2 = obj[o].split(';')[2]
							this.ruleForm.weizhi = obj[o];
							this.ro.weizhi = true;
							continue;
						}
						if(o=='fuwuneirong'){
							this.ruleForm.fuwuneirong = obj[o];
							this.ro.fuwuneirong = true;
							continue;
						}
						if(o=='yuyueriqi'){
							this.ruleForm.yuyueriqi = obj[o];
							this.ro.yuyueriqi = true;
							continue;
						}
						if(o=='yuyueshiduan'){
							this.ruleForm.yuyueshiduan = obj[o];
							this.ro.yuyueshiduan = true;
							continue;
						}
						if(o=='yuyueshijian'){
							this.ruleForm.yuyueshijian = obj[o];
							this.ro.yuyueshijian = true;
							continue;
						}
						if(o=='dingdanzhuangtai'){
							this.ruleForm.dingdanzhuangtai = obj[o];
							this.ro.dingdanzhuangtai = true;
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
						if(o=='fuwudizhi'){
							this.ruleForm.fuwudizhi2 = obj[o].split(';')[2]
							this.ruleForm.fuwudizhi = obj[o];
							this.ro.fuwudizhi = true;
							continue;
						}
						if(o=='shoujihao'){
							this.ruleForm.shoujihao = obj[o];
							this.ro.shoujihao = true;
							continue;
						}
					}
					this.ruleForm.dingdanzhuangtai = '预约中'; 				}

				// 获取用户信息
				this.$http({
					url: `${this.sessionTable}/session`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						var json = data.data;
						if(this.sessionTable!="users") {
							this.ro.yuyueshijian = true;
						}
						if(((json.yonghuzhanghao!=''&&json.yonghuzhanghao) || json.yonghuzhanghao==0) && this.sessionTable!="users"){
							this.ruleForm.yonghuzhanghao = json.yonghuzhanghao
							this.ro.yonghuzhanghao = true;
						}
						if(((json.yonghuxingming!=''&&json.yonghuxingming) || json.yonghuxingming==0) && this.sessionTable!="users"){
							this.ruleForm.yonghuxingming = json.yonghuxingming
							this.ro.yonghuxingming = true;
						}
					} else {
						this.$message.error(data.msg);
					}
				});
				this.yuyueshiduanOptions = "上午,下午".split(',')
				this.dingdanzhuangtaiOptions = "已派单,已取消,预约中".split(',')
			
			},
			// 多级联动参数

			async info(id) {
				await this.$http({
					url: `fuwuyuyue/info/${id}`,
					method: "get"
				}).then(({ data }) => {
					if (data && data.code === 0) {
						this.ruleForm = data.data;
						//解决前台上传图片后台不显示的问题
						let reg=new RegExp('../../../upload','g')//g代表全部
						this.ruleForm.weizhi2 = this.ruleForm.weizhi.split(';')[2]
						this.ruleForm.fuwudizhi2 = this.ruleForm.fuwudizhi.split(';')[2]
					} else {
						this.$message.error(data.msg);
					}
				});
			},

			// 提交
			async onSubmit() {
					await this.$refs["ruleForm"].validate(async valid => {
						if (valid) {
							if(!this.ruleForm.id) {
								this.ruleForm.ispay = '未支付'
							}
							if(this.ruleForm.yuyuebianhao) {
								this.ruleForm.yuyuebianhao = String(this.ruleForm.yuyuebianhao)
							}
							if(this.ruleForm.xiangmutupian!=null) {
								this.ruleForm.xiangmutupian = this.ruleForm.xiangmutupian.replace(new RegExp(this.$base.url,"g"),"");
							}
							var objcross = this.$storage.getObj('crossObj');
							if(!this.ruleForm.id) {
								delete this.ruleForm.userid
							}
							if(this.type=='cross'){
								var statusColumnName = this.$storage.get('statusColumnName');
								var statusColumnValue = this.$storage.get('statusColumnValue');
								if(statusColumnName!='') {
									var obj = this.$storage.getObj('crossObj');
									if(statusColumnName && !statusColumnName.startsWith("[")) {
										for (var o in obj){
											if(o==statusColumnName){
												obj[o] = statusColumnValue;
											}
										}
										var table = this.$storage.get('crossTable');
										await this.$http({
											url: `${table}/update`,
											method: "post",
											data: obj
										}).then(({ data }) => {});
									}
								}
							}
							let changeType = true
							let errMsg = ''
							await this.$http({
								url: `fuwuyuyue/${!this.ruleForm.id ? "save" : "update"}`,
								method: "post",
								data: this.ruleForm
							}).then(async ({ data }) => {
								if (data && data.code === 0) {
									this.$message({
										message: "操作成功",
										type: "success",
										duration: 1500,
										onClose: () => {
											if(this.isAuth('fuwuyuyue','支付')&&this.type=='cross') {
												this.$confirm('是否跳转支付？').then(_ => {
													this.parent.showFlag = true;
													this.parent.addOrUpdateFlag = false;
													this.parent.fuwuyuyueCrossAddOrUpdateFlag = false;
													this.$router.push('/fuwuyuyue')
												}).catch(_ => {
													this.parent.showFlag = true;
													this.parent.addOrUpdateFlag = false;
													this.parent.fuwuyuyueCrossAddOrUpdateFlag = false;
													this.parent.search();
												});
											}else {
												this.parent.showFlag = true;
												this.parent.addOrUpdateFlag = false;
												this.parent.fuwuyuyueCrossAddOrUpdateFlag = false;
												this.parent.search();
											}
											
										}
									});
								} else {
									this.$message.error(data.msg);
								}
							});
						}
					});
			},
			// 获取uuid
			getUUID () {
				return new Date().getTime();
			},
			// 返回
			back() {
				this.parent.showFlag = true;
				this.parent.addOrUpdateFlag = false;
				this.parent.fuwuyuyueCrossAddOrUpdateFlag = false;
			},
			xiangmutupianUploadChange(fileUrls) {
				this.ruleForm.xiangmutupian = fileUrls;
			},
		}
	};
</script>
<style lang="scss" scoped>
	.addEdit-block {
		padding: 10px;
	}
	.add-update-preview {
		border: 0px solid #e5e7eb;
		border-radius: 10px;
		padding: 50px 20% 30px 10%;
		box-shadow: none;
		background: #fff;
		display: flex;
		flex-wrap: wrap;
	}
	.amap-wrapper {
		width: 100%;
		height: 500px;
	}
	
	.search-box {
		position: absolute;
	}
	
	.el-date-editor.el-input {
		width: auto;
	}
	.add-update-preview ::v-deep .el-form-item {
		margin: 0 0 20px 0;
		width: 48%;
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
	.add-update-preview ::v-deep .el-form-item.editorBox {
		margin: 0 0 20px 0;
		width: 100%;
	}
	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		font-weight: 500;
		width: 180px;
		font-size: 14px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.editorBox ::v-deep .el-form-item__content {
		margin: 0;
		display: flex;
		align-items: center;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor {
		box-shadow: none;
		max-width: 100% !important;
		width: 100%;
		height: auto;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-toolbar {
		border: 1px solid #eee;
		border-radius: 4px 4px 0 0;
		background: none;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container {
		border: 1px solid #eee;
		border-radius: 0 0 4px 4px;
		background: none;
		min-height: 200px;
		border-width: 0px 1px 1px;
	}
	.add-update-preview ::v-deep.el-form-item.editorBox .editor .ql-container .ql-blank::before {
		color: #999;
	}
	
	.add-update-preview ::v-deep .el-form-item.textBox {
		margin: 0 0 20px 0;
		width: 100%;
	}
	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.textBox ::v-deep .el-form-item__content {
		margin: 0;
		display: flex;
		align-items: center;
	}
	.add-update-preview ::v-deep.el-form-item.textBox span.text {
		padding: 0;
		color: #666;
		font-weight: 500;
		display: inline-block;
		font-size: 16px;
		line-height: 40px;
	}
	
	.add-update-preview .el-input {
		width: 100%;
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
	.add-update-preview .el-input ::v-deep .el-input__inner[readonly="readonly"] {
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number {
		text-align: left;
		width: 100%;
	}
	.add-update-preview .el-input-number ::v-deep .el-input__inner {
		text-align: left;
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
	.add-update-preview .el-input-number ::v-deep .is-disabled .el-input__inner {
		text-align: left;
		border: 0px solid #ddd;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 12px;
		box-shadow: none;
		outline: none;
		color: #999;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-input-number ::v-deep .el-input-number__decrease {
		display: none;
	}
	.add-update-preview .el-input-number ::v-deep .el-input-number__increase {
		display: none;
	}
	.add-update-preview .el-select {
		width: 100%;
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
	.add-update-preview .el-select ::v-deep .is-disabled .el-input__inner {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: #f5f5f5;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .el-date-editor {
		width: 100%;
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
	.add-update-preview .el-date-editor ::v-deep .el-input__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 10px 0 30px;
		box-shadow: none;
		outline: none;
		color: #999;
		background: #f5f5f5;
		width: 100%;
		font-size: 16px;
		height: 40px;
	}
	.add-update-preview .viewBtn {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #fff;
		background: #2dc7a5;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .viewBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .downBtn {
		border: 0;
		cursor: pointer;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #fff;
		background: #2dc7a5;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #fff;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .downBtn:hover {
		opacity: 0.8;
	}
	.add-update-preview .unBtn {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 0 15px;
		margin: 0 20px 0 0;
		outline: none;
		color: #000;
		background: #eee;
		width: auto;
		font-size: 14px;
		line-height: 40px;
		height: 40px;
		.iconfont {
			margin: 0 2px;
			color: #000;
			font-size: 14px;
			height: 40px;
		}
	}
	.add-update-preview .unBtn:hover {
		opacity: 0.8;
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
	
	.add-update-preview ::v-deep .upload .upload-img {
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
	.add-update-preview ::v-deep .el-upload__tip {
		color: #838fa1;
	}
	.add-update-preview ::v-deep .el-form-item.fileupload {
		margin: 0 0 20px 0;
		width: 48%;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.fileupload ::v-deep .el-form-item__content {
		margin: 0 0 0 80px;
		display: flex;
		align-items: center;
	}
	.add-update-preview .el-form-item.fileupload ::v-deep .el-upload-dragger {
		border: 1px solid #eee;
		cursor: pointer;
		background-color: #fff;
		border-radius: 6px;
		padding: 0px 20px 20px;
		overflow: hidden;
		width: auto;
		box-sizing: border-box;
		text-align: center;
		height: auto;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-icon-upload {
		margin: 0;
		color: #2dc7a5;
		font-size: 60px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text {
		color: #606266;
		textalign: center;
		fontsize: 14px;
		line-height: 1;
	}
	.add-update-preview .el-form-item.fileupload .el-upload-dragger ::v-deep .el-upload__text em {
		fontstyle: normal;
		color: #000;
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
	.add-update-preview .el-textarea ::v-deep .el-textarea__inner[readonly="readonly"] {
		border: 0;
		cursor: not-allowed;
		border-radius: 4px;
		padding: 12px;
		box-shadow: none;
		outline: none;
		color: #666;
		width: 100%;
		font-size: 16px;
		height: auto;
	}
	.add-update-preview ::v-deep .el-form-item.btn {
		padding: 0;
		margin: 10px auto 0;
		display: flex;
		width: 100%;
		justify-content: center;
		.btn1 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #2dc760;
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
		.btn1:hover {
			opacity: 0.8;
		}
		.btn2 {
			border: 0;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #2d96c7;
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
		.btn2:hover {
			opacity: 0.8;
		}
		.btn3 {
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
		.btn3:hover {
			opacity: 0.8;
		}
		.btn4 {
			border: 0px solid #eee;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #c72d55;
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
		.btn4:hover {
			opacity: 0.8;
		}
		.btn5 {
			border: 0px solid #eee;
			cursor: pointer;
			border-radius: 4px;
			padding: 0 24px;
			margin: 4px;
			outline: none;
			color: #fff;
			background: #c7af2d;
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
		.btn5:hover {
			opacity: 0.8;
		}
	}
	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__label {
		padding: 0 10px 0 0;
		color: #666;
		white-space: nowrap;
		font-weight: 500;
		width: 180px;
		font-size: 16px;
		line-height: 40px;
		text-align: right;
	}
	
	.add-update-preview .el-form-item.btn ::v-deep .el-form-item__content {
	}
</style>
