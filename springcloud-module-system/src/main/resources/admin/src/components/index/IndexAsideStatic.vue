<template>
	<div class="menu-preview">
		<!-- 竖向-2 -->
		<el-scrollbar :wrap-class="isCollapse ? 'scrollbar-wrapper scrollbar-wrapper-close' : 'scrollbar-wrapper scrollbar-wrapper-open'">
			<el-button :style="verticalStyle2[isCollapse?'close':'open'].btn.default" type="primary" @click="collapse">
				<span class="icon iconfont"
					:style="verticalStyle2[isCollapse?'close':'open'].btn.icon.default"
					:class="verticalStyle2[isCollapse?'close':'open'].btn.icon.text"></span>{{verticalStyle2[isCollapse?'close':'open'].btn.text}}
			</el-button>
			<div class="title" :style="verticalStyle2[isCollapse?'close':'open'].title.box.default">
				<el-image v-if="verticalStyle2[isCollapse?'close':'open'].title.img.flag" :style="verticalStyle2[isCollapse?'close':'open'].title.img.default" :src="topLogoUrl?($base.url + topLogoUrl):verticalStyle2[isCollapse?'close':'open'].title.img.url" fit="cover"></el-image>
				<span class="title-name" :style="verticalStyle2[isCollapse?'close':'open'].title.text.default">{{verticalStyle2[isCollapse?'close':'open'].title.text.showType?verticalStyle2[isCollapse?'close':'open'].title.text.text:''}} {{this.$project.projectName}}</span>
			</div>
			<div class="userinfo"
				:style="verticalStyle2[isCollapse?'close':'open'].userinfo.box.default">
				<el-image v-if="avatar" :style="verticalStyle2[isCollapse?'close':'open'].userinfo.img.default" :src="avatar?this.$base.url + avatar:require('@/assets/img/avator.png')" fit="cover"></el-image>
				<div :style="verticalStyle2[isCollapse?'close':'open'].userinfo.nickname.default">
				{{this.$storage.get('adminName')}}</div>
			</div>
			<el-menu :default-active="activeMenu" :unique-opened="true" :style="verticalStyle2[isCollapse?'close':'open'].menu.box.default"
				class="el-menu-vertical-2" :collapse-transition="false" :collapse="isCollapse">
				<el-menu-item class="home" :popper-append-to-body="false" popper-class="home" @click.native="menuHandler('')" :style="verticalStyle2[isCollapse?'close':'open'].home.one.box.default" index="/">
					<div class="el-tooltip">
						<i :style="verticalStyle2[isCollapse?'close':'open'].home.one.icon.default"
							class="icon iconfont icon-shouye-zhihui"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].home.one.title.default"
							slot="title">{{verticalStyle2.open.home.one.title.text}}</span>
					</div>
				</el-menu-item>
				<el-submenu class="user" popper-class="user" :popper-append-to-body="false"
					:style="verticalStyle2[isCollapse?'close':'open'].user.one.box.default" index="1">
					<template slot="title">
						<i :style="verticalStyle2[isCollapse?'close':'open'].user.one.icon.default"
							class="icon iconfont icon-kuaijiezhifu"></i>
						<span :style="verticalStyle2[isCollapse?'close':'open'].user.one.title.default"
							slot="title">{{verticalStyle2.open.user.one.title.text}}</span>
					</template>
					<el-menu-item index="/updatePassword" @click="menuHandler('updatePassword')"><i class="el-icon-edit-outline nav-child-icon"></i>修改密码</el-menu-item>
					<el-menu-item index="/center" @click="menuHandler('center')"><i class="el-icon-user nav-child-icon"></i>个人信息</el-menu-item>
				</el-submenu>
				<template v-for="(menu,index) in menuList.backMenu" v-if="menu.child.length&&menu.child[0].tableName!='hasBoard'&&menu.child[0].tableName!='storeup'&&(menu.child[0].tableName.length<=7||(menu.child[0].tableName.length>7&&menu.child[0].tableName.substring(0,7)!='chapter'))">
					<el-submenu v-if="menu.child.length > 1 || !verticalIsMultiple" class="other" popper-class="other" :popper-append-to-body="false" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" :index="index+2+''">
						<template slot="title">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{nameChange(menu.menu, verticalFlag)}}</span>
						</template>
						<el-menu-item v-for="(child,sort) in menu.child" :key="sort" :index="'/index/'+child.tableName" @click="menuHandler(child.tableName)"><i class="el-icon-folder-opened nav-child-icon"></i>{{ child.menu }}</el-menu-item>
					</el-submenu>
					<el-menu-item v-if="menu.child.length <= 1 && verticalIsMultiple" class="other" popper-class="other" :style="verticalStyle2[isCollapse?'close':'open'].menu.one.box.default" @click="menuHandler(menu.child[0].tableName)" :index="'/index/'+menu.child[0].tableName">
						<div class="el-tooltip">
							<i :style="verticalStyle2[isCollapse?'close':'open'].menu.one.icon.default" class="el-icon-menu" :class="icons[index]"></i>
							<span :style="verticalStyle2[isCollapse?'close':'open'].menu.one.title.default" slot="title">{{nameChange(menu.child[0].menu, verticalFlag)}}</span>
						</div>
					</el-menu-item>
				</template>
			</el-menu>
		</el-scrollbar>


	</div>
</template>

<script>
import menu from '@/utils/menu'
export default {
	data() {
		return {
			menuList: [],
			dynamicMenuRoutes: [],
			role: '',
			user: null,
			avatar:'',
			icons: [
				'el-icon-s-cooperation',
				'el-icon-s-order',
				'el-icon-s-platform',
				'el-icon-s-fold',
				'el-icon-s-unfold',
				'el-icon-s-operation',
				'el-icon-s-promotion',
				'el-icon-s-release',
				'el-icon-s-ticket',
				'el-icon-s-management',
				'el-icon-s-open',
				'el-icon-s-shop',
				'el-icon-s-marketing',
				'el-icon-s-flag',
				'el-icon-s-comment',
				'el-icon-s-finance',
				'el-icon-s-claim',
				'el-icon-s-custom',
				'el-icon-s-opportunity',
				'el-icon-s-data',
				'el-icon-s-check',
				'el-icon-s-grid',
				'el-icon-menu',
				'el-icon-chat-dot-square',
				'el-icon-message',
				'el-icon-postcard',
				'el-icon-position',
				'el-icon-microphone',
				'el-icon-close-notification',
				'el-icon-bangzhu',
				'el-icon-time',
				'el-icon-odometer',
				'el-icon-crop',
				'el-icon-aim',
				'el-icon-switch-button',
				'el-icon-full-screen',
				'el-icon-copy-document',
				'el-icon-mic',
				'el-icon-stopwatch',
			],
			menulistBorderBottom: {},
			topLogoUrl: '',
			verticalFlag: false,
			isCollapse: false,
			verticalStyle2: {"isCollapse":false,"close":{"contentBox":{"hover":{},"active":{"margin":"0 0 0 54px"},"default":{"minHeight":"100%","padding":"0","margin":"50px 0 0 64px","position":"relative","display":"block"}},"box":{"hover":{},"active":{"width":"54px"},"default":{"boxShadow":"1px 0 0px  #f6f6f6","overflow":"hidden","top":"0","left":"0","background":"#fff","bottom":"0","width":"64px","fontSize":"0px","position":"fixed","transition":"width 0.3s","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":true,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"44px","fontSize":"12px","color":"rgba(64, 158, 255, 1)"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"#f5f5f5 !important","lineHeight":"40px","color":"#333","height":"40px"},"active":{"padding":"0 20px","backgroundColor":"#f5f5f5 !important","lineHeight":"40px","color":"#333","height":"40px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"40px","color":"#666","height":"40px"}},"box":{"hover":{},"default":{"border":"none"}}},"box":{"hover":{},"default":{"border":0,"padding":"0","listStyle":"none","margin":"0","position":"relative","background":"none"}},"one":{"box1":{"hover":{"color":"#017cfe"},"active":{"color":"#017cfe"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#333","background":"none"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"26px","color":"#6d5b4d","height":"40px"},"text":"icon-menu02"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","margin":"10px 0 10px 10px","outline":"none","color":"#ff7a00","borderRadius":"0","background":"rgba(64, 158, 255, 0)","width":"auto","fontSize":"14px","height":"40px"},"text":""},"user":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"#f5f5f5 !important","lineHeight":"40px","color":"#333","height":"40px"},"active":{"padding":"0 20px","backgroundColor":"#f5f5f5 !important","lineHeight":"40px","color":"#333","height":"40px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"40px","color":"#656","height":"40px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#017cfe"},"active":{"color":"#017cfe"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#323","background":"none"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"fontSize":"24px","lineHeight":"1.5","color":"#fff","textAlign":"center"}},"img":{"hover":{},"default":{"width":"100%","objectFit":"cover","borderRadius":"20px","display":"block","height":"170px"}},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 20px","backgroundColor":"red !important","lineHeight":"56px","color":"#fff","height":"56px"},"active":{"padding":"0 20px","backgroundColor":"blue !important","lineHeight":"56px","color":"#fff","height":"56px"},"default":{"padding":"0 20px","backgroundColor":"#fff","lineHeight":"56px","color":"#646","height":"56px"}},"box":{"hover":{},"default":{"border":"none"}}},"one":{"box1":{"hover":{"color":"#017cfe"},"active":{"color":"#017cfe"},"default":{"cursor":"pointer","padding":"0 20px","whiteSpace":"nowrap","position":"relative","color":"#313","background":"none"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0","color":"inherit","textAlign":"center","display":"inline-block","width":"24px","fontSize":"18px"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"padding":"0","listStyle":"none","margin":"0"}},"title":{"hover":{},"default":{"width":"0","verticalAlign":"middle","fontSize":"14px","color":"inherit","height":"0"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","display":"none","fontSize":"12px","position":"absolute","right":"20px"}}}}},"open":{"contentBox":{"hover":{},"default":{"minHeight":"100%","padding":"60px 0 0 200px","margin":"0","position":"relative","background":"#fff","display":"block"}},"box":{"hover":{},"default":{"boxShadow":"0px 0 3px  #f6f6f6","padding":"10px 0 0 15px","overflow":"hidden","top":"0px","left":"0","background":"#2dc7a3","bottom":"0","width":"200px","position":"fixed","transition":"width 0.3s","height":"100%","zIndex":"1001"}},"title":{"img":{"hover":{},"default":{"width":"44px","objectFit":"cover","borderRadius":"100%","height":"44px"},"flag":false,"url":"http://codegen.caihongy.cn/20201114/7856ba26477849ea828f481fa2773a95.jpg"},"box":{"hover":{},"default":{"width":"100%","padding":"20px","display":"none","height":"auto"}},"text":{"hover":{},"showType":true,"default":{"padding":"0 0 0 12px","lineHeight":"44px","fontSize":"12px","color":"rgba(64, 158, 255, 1)"},"text":"欢迎使用"}},"menu":{"two":{"title":{"hover":{"padding":"0","lineHeight":"42px","textDecoration":"underline","color":"#ff0","background":"none","height":"42px"},"active":{"padding":"0","color":"#ff0","borderRadius":"10px","background":"none","lineHeight":"42px","textDecoration":"underline","height":"42px"},"default":{"padding":"0","color":"#fff","borderRadius":"10px","textAlign":"center","background":"none","lineHeight":"42px","fontSize":"14px","minWidth":"auto","height":"42px"}},"box":{"hover":{},"default":{"border":"none","padding":"10px 0 0","background":"none"}}},"box":{"hover":{},"default":{"border":0,"padding":"10px 0 100px","listStyle":"none","margin":"0","flexWrap":"wrap","background":"none","display":"flex","position":"relative"}},"one":{"box1":{"hover":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"active":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"default":{"cursor":"pointer","padding":"0 0px","borderColor":"#fff","whiteSpace":"nowrap","color":"#fff","textAlign":"center","clipPath":"polygon(8% 0, 100% 0%, 100% 100%, 0% 100%)","background":"linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%)","borderWidth":"0 0 0 0px","lineHeight":"44px","position":"relative","borderStyle":"solid","height":"44px"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 auto","color":"inherit","textAlign":"center","display":"none","width":"auto","fontSize":"18px","lineHeight":"1"},"flag":true},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0 0 10px"}},"title":{"hover":{},"default":{"width":"auto","verticalAlign":"middle","fontSize":"16px","lineHeight":"44px","color":"inherit","textAlign":"center"}},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"10px"}}}},"btn":{"icon":{"hover":{},"default":{"margin":"0 2px","fontSize":"26px","color":"#6d5b4d","height":"40px"},"text":"icon-menu02"},"hover":{"opacity":"0.8"},"default":{"border":"0","cursor":"pointer","padding":"0 9px","outline":"none","margin":"10px 0 10px 10px","color":"#6d5b4d","borderRadius":"4px","background":"rgba(64, 158, 255, 0)","display":"block","width":"auto","fontSize":"14px","height":"40px"},"text":""},"user":{"two":{"title":{"hover":{"padding":"0","lineHeight":"42px","textDecoration":"underline","color":"#ff0","background":"none","height":"42px"},"active":{"padding":"0","color":"#ff0","borderRadius":"10px","background":"none","lineHeight":"42px","textDecoration":"underline","height":"42px"},"default":{"padding":"0","color":"#fff","borderRadius":"10px","textAlign":"center","background":"none","lineHeight":"42px","fontSize":"14px","minWidth":"auto","height":"42px"}},"box":{"hover":{},"default":{"border":"none","padding":"10px 0 0","background":"none"}}},"one":{"box1":{"hover":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"active":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"default":{"cursor":"pointer","padding":"0 0px","borderColor":"#fff","whiteSpace":"nowrap","color":"#fff","textAlign":"center","clipPath":"polygon(8% 0, 100% 0%, 100% 100%, 0% 100%)","background":"linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%)","borderWidth":"0 0 0 0px","lineHeight":"44px","position":"relative","borderStyle":"solid","height":"44px"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 auto","color":"inherit","textAlign":"center","display":"none","width":"auto","fontSize":"18px","lineHeight":"1"},"flag":true,"text":"icon-kuaijiezhifu"},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0 0 10px","order":"22"}},"title":{"hover":{},"default":{"width":"auto","verticalAlign":"middle","fontSize":"16px","lineHeight":"44px","color":"inherit","textAlign":"center"},"text":"个人中心"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"10px"}}}},"userinfo":{"nickname":{"hover":{},"default":{"color":"#fff","fontSize":"18px","lineHeight":"2"}},"img":{"hover":{},"default":{"width":"60px","margin":"0 5px 0 0","objectFit":"cover","borderRadius":"100%","display":"block","height":"60px"}},"box":{"hover":{},"default":{"padding":"20px 10px 10px","borderColor":"#eeeeee30","alignItems":"center","textAlign":"center","borderWidth":"0 0 1px 0","background":"none","flexDirection":"column","display":"none","width":"100%","borderStyle":"solid","justifyContent":"center","height":"auto"}}},"home":{"two":{"title":{"hover":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"red","height":"50px"},"active":{"padding":"0 40px","lineHeight":"50px","color":"#fff","background":"blue","height":"50px"},"default":{"padding":"0 40px","lineHeight":"50px","color":"#664","background":"#fff","height":"50px"}},"box":{"hover":{},"default":{"border":"none","display":"none"}}},"one":{"box1":{"hover":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"active":{"borderColor":"#017cfe","color":"#2dc7a3","borderStyle":"solid","background":"url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff","borderWidth":"0 0 0px"},"default":{"cursor":"pointer","padding":"0 0px","borderColor":"#fff","whiteSpace":"nowrap","color":"#fff","textAlign":"center","clipPath":"polygon(8% 0, 100% 0%, 100% 100%, 0% 100%)","background":"linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%)","borderWidth":"0 0 0 0px","lineHeight":"44px","position":"relative","borderStyle":"solid","height":"44px"}},"icon":{"hover":{},"default":{"verticalAlign":"middle","margin":"0 auto","color":"inherit","textAlign":"center","display":"none","width":"auto","fontSize":"18px","lineHeight":"1"},"flag":true,"text":"icon-shouye-zhihui"},"box":{"hover":{},"default":{"width":"100%","padding":"0","listStyle":"none","margin":"0 0 10px"}},"title":{"hover":{},"default":{"width":"auto","verticalAlign":"middle","fontSize":"16px","lineHeight":"44px","color":"inherit","textAlign":"center"},"text":"系统首页"},"arrow":{"hover":{},"default":{"verticalAlign":"middle","margin":"-7px 0 0 0","top":"50%","color":"inherit","fontSize":"12px","position":"absolute","right":"20px"}}}}}},
			verticalIsMultiple: true,
		}
	},
	computed: {
		activeMenu() {
			const route = this.$route
			const {
				meta,
				path
			} = route
			// if st path, the sidebar will highlight the path you sete
			if (meta.activeMenu) {
				return meta.activeMenu
			}
			return path
		}
	},
	watch:{
		avatar(){
			this.$forceUpdate()
		},
	},
	mounted() {
		let menus = menu.list()
			if(menus) {
				let arr = []
				let brr = []
				menus.forEach(item=>{
				if(item.roleName == this.$storage.get('role')) {
  					item.backMenu.forEach(item1=>{
						if(item1.child&&item1.child.length>1 ) {
							item1.child.forEach(item2=>{
								if(item2.tableName=='fuwuxinxi') {
									if(
										this.isAuth('fuwuxinxi','服务类型占比')
										||this.isAuth('fuwuxinxi','服务项目收藏量')
										||this.isAuth('fuwuxinxi','服务项目评分值统计')
										||this.isAuth('fuwuxinxi','服务项目点赞量统计')
									) {
										arr.push({
											allButtons: ['查看'],
											buttons: ['查看'],
											menu: '服务信息统计',
											tableName: 'fuwuxinxistat',
										})
									}
								}
							})
						}else if(item1.child&&item1.child.length==1 ) {
							if(item1.menu == '服务信息管理') {
								if(
									this.isAuth('fuwuxinxi','服务类型占比')
									||this.isAuth('fuwuxinxi','服务项目收藏量')
									||this.isAuth('fuwuxinxi','服务项目评分值统计')
									||this.isAuth('fuwuxinxi','服务项目点赞量统计')
								) {
									arr.push({
										allButtons: ['查看'],
										buttons: ['查看'],
										menu: '服务信息统计',
										tableName: 'fuwuxinxistat',
									})
								}
							}
						}
					})
  					item.backMenu.forEach(item1=>{
						if(item1.child&&item1.child.length>1 ) {
							item1.child.forEach(item2=>{
								if(item2.tableName=='fuwuyuyue') {
									if(
										this.isAuth('fuwuyuyue','预约金额统计')
										||this.isAuth('fuwuyuyue','预约服务类型统计')
									) {
										arr.push({
											allButtons: ['查看'],
											buttons: ['查看'],
											menu: '服务预约统计',
											tableName: 'fuwuyuyuestat',
										})
									}
								}
							})
						}else if(item1.child&&item1.child.length==1 ) {
							if(item1.menu == '服务预约管理') {
								if(
									this.isAuth('fuwuyuyue','预约金额统计')
									||this.isAuth('fuwuyuyue','预约服务类型统计')
								) {
									arr.push({
										allButtons: ['查看'],
										buttons: ['查看'],
										menu: '服务预约统计',
										tableName: 'fuwuyuyuestat',
									})
								}
							}
						}
					})
  					item.backMenu.forEach(item1=>{
						if(item1.child&&item1.child.length>1 ) {
							item1.child.forEach(item2=>{
								if(item2.tableName=='shourumingxi') {
									if(
										this.isAuth('shourumingxi','月收益统计')
									) {
										item1.child.push({
											allButtons: ['查看'],
											buttons: ['查看'],
											menu: '收入明细统计',
											tableName: 'shourumingxistat',
										})
									}
								}
							})
						}else if(item1.child&&item1.child.length==1 ) {
							if(item1.menu == '收入明细管理') {
								if(
									this.isAuth('shourumingxi','月收益统计')
								) {
									item1.child.push({
										allButtons: ['查看'],
										buttons: ['查看'],
										menu: '收入明细统计',
										tableName: 'shourumingxistat',
									})
								}
							}
						}
					})
				}
			})
			if(arr.length) {
				let crr = {
					menu: '统计管理',
					child: arr
				}
				menus.forEach(item=>{
					if(item.roleName == this.$storage.get('role')) {
						item.backMenu.splice(0,0,crr)
					}
				})
			}
			
			this.menuList = this.cleanStatMenus(menus)
		} else {
			let params = {
				page: 1,
				limit: 1,
				sort: 'id',
			}
			
			this.$http({
				url: "menu/list",
				method: "get",
				params: params
			}).then(({
				data
			}) => {
				if (data && data.code === 0) {
					let arr = []
					let brr = []
					menus = JSON.parse(data.data.list[0].menujson)
					menus.forEach(item=>{
						if(item.roleName == this.$storage.get('role')) {
  							item.backMenu.forEach(item1=>{
								if(item1.child&&item1.child.length>1 ) {
									item1.child.forEach(item2=>{
										if(item2.tableName=='fuwuxinxi') {
											if(
												this.isAuth('fuwuxinxi','服务类型占比')
												||this.isAuth('fuwuxinxi','服务项目收藏量')
												||this.isAuth('fuwuxinxi','服务项目评分值统计')
												||this.isAuth('fuwuxinxi','服务项目点赞量统计')
											) {
												arr.push({
													allButtons: ['查看'],
													buttons: ['查看'],
													menu: '服务信息统计',
													tableName: 'fuwuxinxistat',
												})
											}
										}
									})
								}else if(item1.child&&(item1.child.length==1 )) {
									if(item1.menu == '服务信息管理') {
										if(
											this.isAuth('fuwuxinxi','服务类型占比')
											||this.isAuth('fuwuxinxi','服务项目收藏量')
											||this.isAuth('fuwuxinxi','服务项目评分值统计')
											||this.isAuth('fuwuxinxi','服务项目点赞量统计')
										) {
											arr.push({
												allButtons: ['查看'],
												buttons: ['查看'],
												menu: '服务信息统计',
												tableName: 'fuwuxinxistat',
											})
										}
									}
								}
							})
  							item.backMenu.forEach(item1=>{
								if(item1.child&&item1.child.length>1 ) {
									item1.child.forEach(item2=>{
										if(item2.tableName=='fuwuyuyue') {
											if(
												this.isAuth('fuwuyuyue','预约金额统计')
												||this.isAuth('fuwuyuyue','预约服务类型统计')
											) {
												arr.push({
													allButtons: ['查看'],
													buttons: ['查看'],
													menu: '服务预约统计',
													tableName: 'fuwuyuyuestat',
												})
											}
										}
									})
								}else if(item1.child&&(item1.child.length==1 )) {
									if(item1.menu == '服务预约管理') {
										if(
											this.isAuth('fuwuyuyue','预约金额统计')
											||this.isAuth('fuwuyuyue','预约服务类型统计')
										) {
											arr.push({
												allButtons: ['查看'],
												buttons: ['查看'],
												menu: '服务预约统计',
												tableName: 'fuwuyuyuestat',
											})
										}
									}
								}
							})
  							item.backMenu.forEach(item1=>{
								if(item1.child&&item1.child.length>1 ) {
									item1.child.forEach(item2=>{
										if(item2.tableName=='shourumingxi') {
											if(
												this.isAuth('shourumingxi','月收益统计')
											) {
												item1.child.push({
													allButtons: ['查看'],
													buttons: ['查看'],
													menu: '收入明细统计',
													tableName: 'shourumingxistat',
												})
											}
										}
									})
								}else if(item1.child&&(item1.child.length==1 )) {
									if(item1.menu == '收入明细管理') {
										if(
											this.isAuth('shourumingxi','月收益统计')
										) {
											item1.child.push({
												allButtons: ['查看'],
												buttons: ['查看'],
												menu: '收入明细统计',
												tableName: 'shourumingxistat',
											})
										}
									}
								}
							})
						}
					})
					if(arr.length) {
						let crr = {
							menu: '统计管理',
							child: arr
						}
						menus.forEach(item=>{
							if(item.roleName == this.$storage.get('role')) {
								item.backMenu.splice(0,0,crr)
							}
						})
					}
					this.menuList = this.cleanStatMenus(menus);
					this.$storage.set("menus", this.menuList);
				}
			})
		}
		this.role = this.$storage.get('role')
		
		for(let i=0;i<this.menuList.length;i++) {
			if(this.menuList[i].roleName == this.role) {
				this.menuList = this.menuList[i];
				break;
			}
		}
		this.styleChange()
		
		let sessionTable = this.$storage.get("sessionTable")
		this.$http({
			url: sessionTable + '/session',
			method: "get"
		}).then(({
			data
		}) => {
			if (data && data.code === 0) {
				if(sessionTable == 'yonghu') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable == 'fuwurenyuan') {
					this.avatar = data.data.touxiang
				}
				if(sessionTable=='users') {
					this.avatar = data.data.image
				}
				this.user = data.data;
			} else {
				let message = this.$message
				message.error(data.msg);
			}
		});
	},
	created(){
		this.icons.sort(()=>{
			return (0.5-Math.random())
		})
	},
  methods: {
    cleanStatMenus(menus) {
      return (menus || []).map(roleMenu => {
        if (!roleMenu.backMenu) return roleMenu
        roleMenu.backMenu = roleMenu.backMenu
          .filter(item => item.menu !== '统计管理')
          .map(item => {
            if (item.child) {
              item.child = item.child.filter(child => !['fuwuxinxistat', 'fuwuyuyuestat', 'shourumingxistat'].includes(child.tableName))
            }
            return item
          })
        return roleMenu
      })
    },
    nameChange(e,type) {
      if(e=='订单管理') {
        return e
      }
      if(type) {
        return e + '管理'
      }
      return e
    },
    collapse() {
      this.isCollapse = !this.isCollapse
      this.$emit('oncollapsechange', this.isCollapse)
    },
    styleChange() {
      this.$nextTick(() => {
        document.querySelectorAll('.el-menu-vertical-demo .el-submenu .el-menu').forEach(el => {
          el.removeAttribute('style')
          const icon = {"border":"none","display":"none"}
          Object.keys(icon).forEach((key) => {
            el.style[key] = icon[key]
          })
        })
      })
    },
    // 菜单跳转：已同步改为 /index 前缀
    menuHandler(name) {
      let router = this.$router
      // 空 = 首页 → /index
      if (!name || name === '') {
        router.push('/index')
      } else {
        // 其他菜单 → /index/xxx
        router.push('/index/' + name)
      }
    },
  }
}
</script>
<style lang="scss" scoped>
	.menu-preview {
		.el-scrollbar {
			height: 100%;
	
			& ::v-deep .scrollbar-wrapper {
				overflow-x: hidden;
			}
		
			// 竖向
			.el-menu-vertical-demo {
				.el-submenu:first-of-type ::v-deep .el-submenu__title .el-submenu__icon-arrow {
					display: none;
				}
			}
			
			.el-menu-vertical-demo>.el-menu-item {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo>.el-menu-item:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title {
				cursor: pointer;
				padding: 0 20px;
				color: #333;
				white-space: nowrap;
				background: #fff;
				position: relative;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title:hover {
				color: #fff;
				background: blue;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
				margin: -7px 0 0 0;
				top: 50%;
				color: inherit;
				vertical-align: middle;
				font-size: 12px;
				position: absolute;
				right: 20px;
			}
			
			.el-menu-vertical-demo .el-submenu {
				padding: 0;
				margin: 0;
				list-style: none;
			}
			
			// .el-menu-vertical-demo .el-submenu ::v-deep .el-menu {
// 					// 		border: none;
// 					// 		display: none;
// 					// }
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item {
				padding: 0 40px;
				color: #666;
				background: #fff;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item:hover {
				padding: 0 40px;
				color: #fff;
				background: red;
				line-height: 50px;
				height: 50px;
			}
			
			.el-menu-vertical-demo .el-submenu ::v-deep .el-menu .el-menu-item.is-active {
				padding: 0 40px;
				color: #fff;
				background: blue;
				line-height: 50px;
				height: 50px;
			}
			// 竖向
		}
	}
	// 竖向 样式二-open
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home {
		font-size: inherit;
		background: none;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		padding: 0 0px;
		color: #fff;
		white-space: nowrap;
		border-color: #fff;
		line-height: 44px;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%);
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%);
		border-width: 0 0 0 0px;
		position: relative;
		border-style: solid;
		text-align: center;
		height: 44px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 0px !important;
		color: #fff !important;
		white-space: nowrap !important;
		border-color: #fff !important;
		line-height: 44px !important;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%) !important;
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%) !important;
		border-width: 0 0 0 0px !important;
		position: relative !important;
		border-style: solid !important;
		text-align: center !important;
		height: 44px !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
		margin: 0 auto;
		color: inherit;
		display: none;
		vertical-align: middle;
		width: auto;
		font-size: 18px;
		line-height: 1;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 10px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.other .el-menu {
		border: none;
		padding: 10px 0 0;
		background: none;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item {
		border-radius: 10px !important;
		padding: 0 !important;
		color: #fff !important;
		background: none !important;
		font-size: 14px !important;
		line-height: 42px !important;
		text-align: center !important;
		min-width: auto !important;
		height: 42px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover {
		padding: 0 !important;
		color: #ff0 !important;
		background: none !important;
		text-decoration: underline !important;
		line-height: 42px !important;
		height: 42px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active {
		border-radius: 10px !important;
		padding: 0 !important;
		color: #ff0 !important;
		background: none !important;
		text-decoration: underline !important;
		line-height: 42px !important;
		height: 42px !important;
	}

	// 竖向 样式二-close
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: none;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		white-space: nowrap !important;
		background: none !important;
		position: relative !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
		color: #017cfe !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item:hover {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.other .el-menu--vertical.other .el-menu-item.is-active {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}
	
	// 竖向 样式二-open-首页
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		padding: 0 0px;
		color: #fff;
		white-space: nowrap;
		border-color: #fff;
		line-height: 44px;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%);
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%);
		border-width: 0 0 0 0px;
		position: relative;
		border-style: solid;
		text-align: center;
		height: 44px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		color: #2dc7a3;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff;
		border-color: #017cfe;
		border-width: 0 0 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		color: #2dc7a3;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff;
		border-color: #017cfe;
		border-width: 0 0 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 0px !important;
		color: #fff !important;
		white-space: nowrap !important;
		border-color: #fff !important;
		line-height: 44px !important;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%) !important;
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%) !important;
		border-width: 0 0 0 0px !important;
		position: relative !important;
		border-style: solid !important;
		text-align: center !important;
		height: 44px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
		margin: 0 auto;
		color: inherit;
		display: none;
		vertical-align: middle;
		width: auto;
		font-size: 18px;
		line-height: 1;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 10px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
		padding: 10px 0 0;
		background: none;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		border-radius: 10px;
		padding: 0;
		color: #fff;
		background: none;
		font-size: 14px;
		line-height: 42px;
		text-align: center;
		min-width: auto;
		height: 42px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
		padding: 0;
		color: #ff0;
		background: none;
		text-decoration: underline;
		line-height: 42px;
		height: 42px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		border-radius: 10px;
		padding: 0;
		color: #ff0;
		background: none;
		text-decoration: underline;
		line-height: 42px;
		height: 42px;
	}
	
	// 竖向 样式二-close-首页
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: none;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: none;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item:hover {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.home .el-menu--vertical.home .el-menu-item.is-active {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}
	
	// 竖向 样式二-open-个人中心
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		padding: 0 0px;
		color: #fff;
		white-space: nowrap;
		border-color: #fff;
		line-height: 44px;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%);
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%);
		border-width: 0 0 0 0px;
		position: relative;
		border-style: solid;
		text-align: center;
		height: 44px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		color: #2dc7a3;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff;
		border-color: #017cfe;
		border-width: 0 0 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		color: #2dc7a3;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff;
		border-color: #017cfe;
		border-width: 0 0 0px;
		border-style: solid;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 0px !important;
		color: #fff !important;
		white-space: nowrap !important;
		border-color: #fff !important;
		line-height: 44px !important;
		clip-path: polygon(8% 0, 100% 0%, 100% 100%, 0% 100%) !important;
		background: linear-gradient(90deg, #ffffff80 0%, #ffffff00 100%) !important;
		border-width: 0 0 0 0px !important;
		position: relative !important;
		border-style: solid !important;
		text-align: center !important;
		height: 44px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
		color: #2dc7a3 !important;
		background: url(http://codegen.caihongy.cn/20260126/1156f6cbfde842fa8d003525ea0ce732.png) no-repeat 10px center / auto 100%,#fff !important;
		border-color: #017cfe !important;
		border-width: 0 0 0px !important;
		border-style: solid !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
		margin: 0 auto;
		color: inherit;
		display: none;
		vertical-align: middle;
		width: auto;
		font-size: 18px;
		line-height: 1;
		text-align: center;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 10px;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 ::v-deep .el-submenu.user .el-menu {
		border: none;
		padding: 10px 0 0;
		background: none;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item {
		border-radius: 10px !important;
		padding: 0 !important;
		color: #fff !important;
		background: none !important;
		font-size: 14px !important;
		line-height: 42px !important;
		text-align: center !important;
		min-width: auto !important;
		height: 42px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover {
		padding: 0 !important;
		color: #ff0 !important;
		background: none !important;
		text-decoration: underline !important;
		line-height: 42px !important;
		height: 42px !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active {
		border-radius: 10px !important;
		padding: 0 !important;
		color: #ff0 !important;
		background: none !important;
		text-decoration: underline !important;
		line-height: 42px !important;
		height: 42px !important;
	}
	
	// 竖向 样式二-close-个人中心
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip {
		cursor: pointer;
		padding: 0 20px;
		color: #333;
		white-space: nowrap;
		background: none;
		position: relative;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user>.el-tooltip:hover {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2>.el-menu-item.user.is-active>.el-tooltip {
		color: #017cfe;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title {
		cursor: pointer !important;
		padding: 0 20px !important;
		color: #333 !important;
		white-space: nowrap !important;
		background: none !important;
		position: relative !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover {
		color: #017cfe !important;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .iconfont {
		margin: 0;
		color: inherit;
		display: inline-block;
		vertical-align: middle;
		width: 24px;
		font-size: 18px;
		text-align: center;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		margin: -7px 0 0 0;
		top: 50%;
		color: inherit;
		display: none;
		vertical-align: middle;
		font-size: 12px;
		position: absolute;
		right: 20px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu {
		border: none;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item {
		background-color: #fff;
		padding: 0 20px;
		color: #666;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item:hover {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}
	
	.scrollbar-wrapper-close .el-menu-vertical-2 .el-submenu.user .el-menu--vertical.user .el-menu-item.is-active {
		background-color: #f5f5f5 !important;
		padding: 0 20px;
		color: #333;
		line-height: 40px;
		height: 40px;
	}

	.menu-preview {
		background: #2f4156;
		color: #b8cbe0;
		font-family: "Microsoft YaHei", "PingFang SC", sans-serif;
	}

	.menu-preview ::v-deep .el-scrollbar__wrap,
	.menu-preview ::v-deep .scrollbar-wrapper-open,
	.menu-preview ::v-deep .scrollbar-wrapper-close,
	.menu-preview ::v-deep .el-menu {
		background: #2f4156 !important;
	}

	.menu-preview ::v-deep .el-scrollbar__bar {
		display: none;
	}

	.menu-preview .el-button {
		display: none !important;
	}

	.menu-preview .title,
	.menu-preview .userinfo {
		display: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 {
		padding-top: 72px !important;
		border: 0 !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu {
		margin: 0 !important;
		background: transparent !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		clip-path: none !important;
		height: 54px !important;
		line-height: 54px !important;
		padding: 0 22px !important;
		text-align: left !important;
		color: #c4d6e8 !important;
		background: #2f4156 !important;
		border: 0 !important;
		font-size: 15px !important;
		font-weight: 500 !important;
		letter-spacing: .5px;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title {
		color: #fff !important;
		background: linear-gradient(90deg, rgba(251, 106, 151, .32), rgba(251, 106, 151, .08)) !important;
		border-left: 4px solid #ff8ab1 !important;
		padding-left: 18px !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu .el-menu .el-menu-item {
		height: 44px !important;
		line-height: 44px !important;
		padding: 0 20px 0 56px !important;
		border-radius: 0 !important;
		text-align: left !important;
		color: #b8cbe0 !important;
		background: #223346 !important;
		font-size: 14px !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu .el-menu .el-menu-item.is-active {
		color: #ffb6cf !important;
		background: rgba(251, 106, 151, .16) !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		right: 22px !important;
		color: #7f98ae !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .el-icon-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item .el-icon-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item .iconfont {
		display: inline-block !important;
		width: 22px !important;
		margin-right: 12px !important;
		color: #a9bfd4 !important;
		font-size: 15px !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		border-left: 4px solid transparent !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover {
		background: linear-gradient(90deg, rgba(255, 111, 159, .38), rgba(255, 138, 177, .08)) !important;
		border-left-color: #ff6f9f !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		position: relative !important;
		width: calc(100% - 24px) !important;
		margin: 6px 12px !important;
		padding: 0 14px 0 42px !important;
		border-radius: 10px !important;
		line-height: 42px !important;
		height: 42px !important;
		text-align: left !important;
		color: #d8e5f3 !important;
		background: rgba(255, 255, 255, .04) !important;
		box-shadow: inset 0 0 0 1px rgba(255, 255, 255, .04) !important;
		font-size: 14px !important;
		font-weight: 500 !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item::before {
		content: "";
		position: absolute;
		left: 18px;
		top: 50%;
		width: 7px;
		height: 7px;
		border-radius: 50%;
		background: #7f98ae;
		transform: translateY(-50%);
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		color: #fff !important;
		background: linear-gradient(90deg, rgba(255, 111, 159, .42), rgba(255, 182, 207, .16)) !important;
		box-shadow: inset 0 0 0 1px rgba(255, 182, 207, .36), 0 8px 18px rgba(26, 38, 52, .18) !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active::before {
		background: #ffd6e4;
		box-shadow: 0 0 0 4px rgba(255, 111, 159, .18);
	}

	.menu-preview,
	.menu-preview ::v-deep .el-scrollbar__wrap,
	.menu-preview ::v-deep .scrollbar-wrapper-open,
	.menu-preview ::v-deep .scrollbar-wrapper-close,
	.menu-preview ::v-deep .el-menu {
		background: linear-gradient(180deg, #ffe3ee 0%, #ffd2e4 48%, #ffc3db 100%) !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 {
		padding: 72px 10px 18px !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu {
		margin: 8px 0 !important;
		border-radius: 16px !important;
		overflow: hidden !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		height: 50px !important;
		line-height: 50px !important;
		padding: 0 18px !important;
		border: 1px solid rgba(255, 255, 255, .68) !important;
		border-left: 0 !important;
		border-radius: 16px !important;
		color: #8a3c59 !important;
		background: rgba(255, 255, 255, .54) !important;
		box-shadow: 0 10px 22px rgba(255, 107, 151, .12) !important;
		font-weight: 600 !important;
		text-align: left !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title {
		color: #fff !important;
		background: linear-gradient(135deg, #ff72a4 0%, #ff93bb 100%) !important;
		border-color: rgba(255, 255, 255, .8) !important;
		box-shadow: 0 12px 26px rgba(255, 91, 143, .28) !important;
		padding-left: 18px !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-menu {
		margin: 8px 0 2px !important;
		padding: 8px !important;
		border-radius: 16px !important;
		background: #ffe3ee !important;
		box-shadow: inset 0 0 0 1px rgba(255, 255, 255, .22) !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		width: 100% !important;
		margin: 6px 0 !important;
		padding: 0 12px 0 16px !important;
		border-radius: 12px !important;
		color: rgba(255, 255, 255, .9) !important;
		background: rgba(116, 32, 66, .16) !important;
		box-shadow: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item::before,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item::before {
		display: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
		color: #9c315d !important;
		background: #fff7fb !important;
		box-shadow: 0 8px 18px rgba(141, 36, 82, .18) !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		color: blue !important;
		background: #fff7fb !important;
		box-shadow: 0 8px 18px rgba(141, 36, 82, .18) !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .nav-child-icon {
		width: 22px !important;
		margin: 0 10px 0 0 !important;
		color: inherit !important;
		font-size: 15px !important;
		text-align: center !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		right: 18px !important;
		color: inherit !important;
	}

	/* 粉色家政主题：一级浅粉、二级深粉，覆盖生成器旧样式 */
	.menu-preview,
	.menu-preview ::v-deep .el-scrollbar,
	.menu-preview ::v-deep .el-scrollbar__wrap,
	.menu-preview ::v-deep .scrollbar-wrapper,
	.menu-preview ::v-deep .scrollbar-wrapper-open,
	.menu-preview ::v-deep .el-menu-vertical-2,
	.menu-preview ::v-deep .el-menu {
		background: linear-gradient(180deg, #fff1f7 0%, #ffdce9 48%, #ffc7dc 100%) !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 {
		padding: 76px 12px 18px !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu {
		height: auto !important;
		margin: 9px 0 !important;
		background: transparent !important;
		overflow: visible !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title {
		display: flex !important;
		align-items: center !important;
		height: 48px !important;
		line-height: 48px !important;
		padding: 0 17px !important;
		border: 1px solid rgba(255, 255, 255, .72) !important;
		border-radius: 14px !important;
		color: #8d3a5b !important;
		background: linear-gradient(135deg, rgba(255,255,255,.78), rgba(255,238,246,.58)) !important;
		box-shadow: 0 8px 20px rgba(255, 96, 145, .12) !important;
		font-size: 15px !important;
		font-weight: 600 !important;
		letter-spacing: .2px !important;
		text-align: left !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.home.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other>.el-tooltip:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item.other.is-active>.el-tooltip,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other ::v-deep .el-submenu__title:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other.is-active ::v-deep .el-submenu__title,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.is-opened ::v-deep .el-submenu__title {
		color: #fff !important;
		background: linear-gradient(135deg, #ff6f9f 0%, #ff94bb 100%) !important;
		border-color: rgba(255,255,255,.86) !important;
		box-shadow: 0 10px 24px rgba(255, 89, 142, .28) !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item .el-icon-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2>.el-menu-item .iconfont,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .el-icon-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .iconfont {
		display: inline-block !important;
		width: 22px !important;
		margin: 0 12px 0 0 !important;
		color: inherit !important;
		font-size: 15px !important;
		text-align: center !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-submenu__title .el-submenu__icon-arrow {
		right: 17px !important;
		color: inherit !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu ::v-deep .el-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu {
		margin: 8px 0 0 !important;
		padding: 8px !important;
		border-radius: 14px !important;
		background: #ffe3ee !important; /* 二级导航背景色 */
		box-shadow: inset 0 1px 0 rgba(255,255,255,.26), 0 10px 24px rgba(218, 75, 126, .18) !important;
	}
	
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item {
		display: flex !important;
		align-items: center !important;
		width: 100% !important;
		height: 40px !important;
		line-height: 40px !important;
		margin: 6px 0 !important;
		padding: 0 13px !important;
		border: 1px solid rgba(255,255,255,.18) !important;
		border-radius: 12px !important;
		color: rgba(255,255,255,.92) !important;
		background: rgba(123, 29, 68, .14) !important;
		box-shadow: none !important;
		font-size: 14px !important;
		font-weight: 500 !important;
		text-align: left !important;
		text-decoration: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu .el-menu .el-menu-item::before {
		display: none !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu .el-menu .el-menu-item .nav-child-icon {
		display: inline-block !important;
		width: 22px !important;
		margin: 0 10px 0 0 !important;
		color: inherit !important;
		font-size: 15px !important;
		text-align: center !important;
	}

	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item:hover,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item:hover {
		color: #a73861 !important;
		background: #fff7fb !important;
		border-color: #fff !important;
		box-shadow: 0 8px 18px rgba(133, 35, 76, .18) !important;
		text-decoration: none !important;
	}
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.other .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.user .el-menu .el-menu-item.is-active,
	.scrollbar-wrapper-open .el-menu-vertical-2 .el-submenu.home .el-menu .el-menu-item.is-active {
		color: blue !important;
		background: #fff7fb !important;
		border-color: #fff !important;
		box-shadow: 0 8px 18px rgba(133, 35, 76, .18) !important;
		text-decoration: none !important;
	}
</style>
