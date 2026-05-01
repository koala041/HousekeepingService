<template>
	<view>
		<!-- #ifdef H5 -->
		<view id="amap-container" class="h5-amap"></view>
		<!-- #endif -->
		<!-- #ifndef H5 -->
		<map id="map" style="width: 100%; height: 450px;z-index: -1;" :latitude="latitude" :longitude="longitude" :markers="markers"
			show-location @markertap="markertap"></map>
		<!-- #endif -->
	</view>
</template>

<script>
	// #ifdef H5
	import AMapLoader from '../tmap/index.js';
	if (typeof window !== 'undefined') {
		window._AMapSecurityConfig = {
			securityJsCode: '8848c1c64023c00cf192ce109b97243c'
		};
	}
	// #endif
	// #ifndef H5
	var QQMapWX = require('../../common/qqmap/qqmap-wx-jssdk.min.js');
	var qqmapsdk;
	// #endif
	var self;
	export default {
		name:"maps",
		props:{
			markers: {
				type: Array,
				default: () => {
					return []
				}
			},
			latitude: {
				type: Number,
				default: 0
			},
			longitude: {
				type: Number,
				default: 0
			},
		},
		data() {
			return {
				IsOption: false,
				key: '',
				map: null,
				amapMarkers: []
			};
		},
		watch:{
			markers(newValue){
				console.log(newValue)
				// #ifdef H5
				this.renderAmapMarkers()
				// #endif
			},
			// #ifdef H5
			latitude() {
				this.updateH5Center()
			},
			longitude() {
				this.updateH5Center()
			},
			// #endif
		},
		mounted() {
			// #ifdef H5
			this.initH5Map()
			// #endif
		},
		beforeDestroy() {
			// #ifdef H5
			if (this.map) {
				this.map.destroy()
				this.map = null
			}
			// #endif
		},
		onLoad() {
			self = this
			let that = this
			// #ifndef H5
			// 实例化API核心类
			qqmapsdk = new QQMapWX({
				key: 'Y77BZ-RDDYJ-TKLFM-KPSXB-3AXPJ-QHFD4'
			});
			uni.getLocation({
				altitude: true,
				success(res) {
					// console.log(res)
					self.longitude = res.longitude
					self.latitude = res.latitude
				}
			})
			self.mapCtr = uni.createMapContext('map')
			// #endif
		},
		methods: {
			// #ifdef H5
			initH5Map() {
				AMapLoader.reset()
				AMapLoader.load({
					key: '7a7b5d185ffd9ca30f582ff9349716ea',
					version: '2.0'
				}).then((AMap) => {
					this.map = new AMap.Map('amap-container', {
						zoom: 14,
						center: [this.longitude || 116.397428, this.latitude || 39.90923]
					})
					this.renderAmapMarkers()
				}).catch((e) => {
					console.log(e)
				})
			},
			renderAmapMarkers() {
				if (!this.map || !window.AMap) return
				this.amapMarkers.forEach(marker => marker.setMap(null))
				this.amapMarkers = (this.markers || []).filter(item => item && item.longitude && item.latitude).map((item) => {
					const marker = new window.AMap.Marker({
						position: [item.longitude, item.latitude],
						map: this.map
					})
					if (item.title) {
						marker.setTitle(item.title)
					}
					marker.on('click', () => {
						this.$emit('markertap', item.id)
					})
					return marker
				})
			},
			updateH5Center() {
				if (!this.map || !this.longitude || !this.latitude) return
				this.map.setCenter([this.longitude, this.latitude])
			},
			// #endif
			markertap(e){
				this.$emit('markertap',e.detail.markerId)
			},
			tapOption: function(item) {
				self.latitude = item.location.lat;
				self.longitude = item.location.lng;
				self.markers[0].latitude = item.location.lat;
				self.markers[0].longitude = item.location.lng;
				self.searchKey = item.title
				self.IsOption = false
				self.data = []
				var obj = {}
				obj.latitude = item.location.lat;
				obj.longitude = item.location.lng;
				// console.log(item,"danji",obj)
				self.toLocation(obj)
			},
			openMapApp() {
				let latitude = self.latitude;
				let longitude = self.longitude;
		
				// console.log(latitude)
				uni.openLocation({
					latitude: latitude,
					longitude: longitude,
					success: function(res) {
						// console.log(res);
					}
				});
		
			},
		}
	}
</script>

<style>
	.h5-amap {
		width: 100%;
		height: 450px;
		z-index: 0;
	}
</style>