<template>
	<div>
		<!-- 图片上传组件辅助-->
		<el-upload :class="refname + 'upload'" :action="getActionUrl" name="file" :headers="header" :show-file-list="false"
			:accept="uploadAccept"
			:on-success="uploadSuccess" :on-error="uploadError" :before-upload="beforeUpload"></el-upload>

		<quill-editor class="editor" v-model="value" :ref="myQuillEditor" :options="editorOption"
			@blur="onEditorBlur($event)" @focus="onEditorFocus($event)" @change="onEditorChange($event)"></quill-editor>
	</div>
</template>
<script>
	import {
		quillEditor
	} from "vue-quill-editor-support-insert-table";
	import "quill/dist/quill.core.css";
	import "quill/dist/quill.snow.css";
	import "quill/dist/quill.bubble.css";
	import axios from 'axios'
	// 工具栏配置
	const toolbarOptions = [
		["bold", "italic", "underline", "strike"], // 加粗 斜体 下划线 删除线
		["blockquote", "code-block"], // 引用  代码块
		[{
			header: 1
		}, {
			header: 2
		}], // 1、2 级标题
		[{
			list: "ordered"
		}, {
			list: "bullet"
		}], // 有序、无序列表
		[{
			script: "sub"
		}, {
			script: "super"
		}], // 上标/下标
		[{
			indent: "-1"
		}, {
			indent: "+1"
		}], // 缩进
		// [{'direction': 'rtl'}],                         // 文本方向
		[{
			size: ["small", false, "large", "huge"]
		}], // 字体大小
		[{
			header: [1, 2, 3, 4, 5, 6, false]
		}], // 标题
		[{
			color: []
		}, {
			background: []
		}], // 字体颜色、字体背景颜色
		[{
			font: []
		}], // 字体种类
		[{
			align: []
		}], // 对齐方式
		["clean"], // 清除文本格式
		["link", "image", "video", "file", "audio", "table", "continuation", "optimize", "proofread", "translation"], // 链接、图片、视频
	];
	var _EditorOption_ = function(page_this) {
		return {
			modules: {
				table: true,
				toolbar: {
					container: toolbarOptions,
					handlers: {
						image(value) {
							if (value) {
								page_this.openUploadByType("image");
							} else {
								this.quill.format("image", false);
							}
						},
						video(value) {
							if (value) {
								page_this.openUploadByType("video");
							} else {
								this.quill.format("video", false);
							}
						},
						file(value) {
							if (value) {
								page_this.openUploadByType("file");
							}
						},
						audio(value) {
							page_this.audioPlay()
						},
						table() {
							const quill = this.quill;
							const numberPattern = /^([1-9]|1\d|20)$/;
							page_this.$prompt("请输入表格行数（1-20）", "插入表格", {
								confirmButtonText: "下一步",
								cancelButtonText: "取消",
								inputValue: "3",
								inputPattern: numberPattern,
								inputErrorMessage: "请输入 1-20 的整数",
							}).then(({
								value: rowValue
							}) => {
								const rows = Number(rowValue);
								return page_this.$prompt("请输入表格列数（1-20）", "插入表格", {
									confirmButtonText: "插入",
									cancelButtonText: "取消",
									inputValue: "3",
									inputPattern: numberPattern,
									inputErrorMessage: "请输入 1-20 的整数",
								}).then(({
									value: colValue
								}) => ({
									rows,
									cols: Number(colValue),
								}));
							}).then(({
								rows,
								cols
							}) => {
								const tableModule = quill.getModule("table");
								if (tableModule && typeof tableModule.insertTable === "function") {
									tableModule.insertTable(rows, cols);
								} else {
									page_this.$message.error("表格模块未加载");
								}
							}).catch(() => {});
						},
						continuation(value) {
							page_this.handleAIContinuation()
						},
						optimize(value) {
							page_this.handleAIOptimize()
						},
						proofread(value) {
							page_this.handleAIProofread()
						},
						translation(value) {
							page_this.handleAITranslation()
						}
					},
				},
			},
			placeholder: "请输入正文",
			initButton: function () {
				//在使用的页面中初始化按钮样式
				const audio= document.querySelector(".ql-audio");
				if (audio) {
					audio.title = '语言朗读'
					audio.style.cssText =
						"width:28px;height: 24px;color: #000";
					audio.className = "icon iconfont icon-gonggao18";
				}
				const table= document.querySelector(".ql-table");
				if (table) {
					table.title = "插入表格";
				}
				const fileBtn = document.querySelector(".ql-file");
				if (fileBtn) {
					fileBtn.title = "上传文件";
				}
				const continuation= document.querySelector(".ql-continuation");
				if (continuation) {
					continuation.title = 'AI续写'
					continuation.style.cssText =
						"width:28px;height: 24px;color: #000";
					continuation.className = "iconfont icon-gerenzhongxin-zhihui";
				}
				
				const optimize= document.querySelector(".ql-optimize");
				if (optimize) {
					optimize.title = 'AI优化'
					optimize.style.cssText =
						"width:28px;height: 24px;color: #000";
					optimize.className = "iconfont icon-renlian01";
				}
				
				const proofread= document.querySelector(".ql-proofread");
				if (proofread) {
					proofread.title = 'AI校对'
					proofread.style.cssText =
						"width:28px;height: 24px;color: #000";
					proofread.className = "iconfont icon-touxiang03";
				}
				
				const translation= document.querySelector(".ql-translation");
				if (translation) {
					translation.title = 'AI翻译'
					translation.style.cssText =
						"width:28px;height: 24px;color: #000";
					translation.className = "iconfont icon-fanhui01";
				}
			},
		};
	};
	export default {
		props: {
			/*编辑器的内容*/
			value: {
				type: String
			},
			action: {
				type: String
			},
			/*图片大小*/
			maxSize: {
				type: Number,
				default: 4000 //kb
			},
			myQuillEditor: {
				type: String
			}
		},

		components: {
			quillEditor
		},
		mounted() {
		    this.editorOption.initButton();
		},
		data() {
			return {
				refname: this.myQuillEditor,
				content: this.value,
				quillUpdateImg: false, // 根据图片上传状态来确定是否显示loading动画，刚开始是false,不显示
				uploadEmbedType: "image", // 当前上传后插入的富文本类型
				editorOption: _EditorOption_(this),

				// serverUrl: `${base.url}sys/storage/uploadSwiper?token=${storage.get('token')}`, // 这里写你要上传的图片服务器地址
				header: {
					// token: sessionStorage.token
					'Token': this.$storage.get("Token")
				} ,// 有的图片服务器要求请求头需要有token
				currentIndex: 0,
				typingInterval: null,
				range: {}
			};
		},
		computed: {
			// 计算属性的 getter
			getActionUrl: function() {
				// return this.$base.url + this.action + "?token=" + this.$storage.get("token");
				return `/${this.$base.name}/` + this.action;
			},
			uploadAccept() {
				if (this.uploadEmbedType === "video") return "video/*";
				if (this.uploadEmbedType === "file") return "*/*";
				return "image/*";
			}
		},
		methods: {
			openUploadByType(type) {
				this.uploadEmbedType = type;
				// 等待 DOM 同步 accept 后再打开选择框，避免沿用上一次类型限制
				this.$nextTick(() => {
					const uploadInput = document.querySelector("." + this.refname + "upload input");
					if (!uploadInput) return;
					uploadInput.setAttribute("accept", this.uploadAccept);
					uploadInput.click();
				});
			},
			audioPlay() {
				var div = document.createElement('div')
				div.innerHTML = this.value
				var text = div.textContent
				const utterance = new SpeechSynthesisUtterance();
				utterance.text = text;  
				utterance.lang = 'zh-CN';  
				utterance.rate = 1.5; // 语速（0.1-10）  
				utterance.pitch = 1.0; // 音调（0-2）  
				utterance.volume = 1.0; // 音量（0-1）  
				window.speechSynthesis.speak(utterance);  
			},
			// 续写
			handleAIContinuation() {
				let that = this
				let loading = this.$loading({
					lock: true,
					text: 'AI续写中...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				var div = document.createElement('div')
				div.innerHTML = this.value
				var text = div.textContent + ' 请将前面的内容续写下去'
				this.$http.post('deepseek/askai', {
					ask: `${text}`,
				}).then(res => {
					if (loading) loading.close()
					that.currentIndex = 0
					typeText(res.data.data)
				});
				
				function typeText(text) {
					let quill = that.$refs[that.refname].quill;
					let length = quill.getLength();
					if (that.currentIndex < text.length) {
						quill.insertText(length - 1, text[that.currentIndex]);
						that.currentIndex++;
						that.typingInterval = setTimeout(()=>{
							typeText(text)
						}, 30); // 打字速度，单位毫秒
					} else {
						clearInterval(that.typingInterval); // 停止打字机效果
						
					}
				}
			},
			// 优化
			handleAIOptimize() {
				let quill = this.$refs[this.refname].quill;
				const range = quill.getSelection()
				let that = this
				if(range.length<=range.index) {
					this.$message.error('请选中需要优化的内容')
					return false
				}
				let loading = this.$loading({
					lock: true,
					text: 'AI优化中...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				var div = document.createElement('div')
				div.innerHTML = this.value
				var text = quill.getText(range.index, range.length) + ' 请帮我优化一下这段文字的内容，并只返回优化的内容'
				this.$http.post('deepseek/askai', {
					ask: `${text}`,
				}).then(res => {
					if (loading) loading.close()
					quill.deleteText(range.index, range.length)
					that.currentIndex = 0
					that.range = range
					typeText(res.data.data)
				});
				
				function typeText(text) {
					let quill = that.$refs[that.refname].quill;
					if (that.currentIndex < text.length) {
						quill.insertText(that.range.index + that.currentIndex, text[that.currentIndex]);
						that.currentIndex++;
						that.typingInterval = setTimeout(()=>{
							typeText(text)
						}, 50); // 打字速度，单位毫秒
					} else {
						clearInterval(that.typingInterval); // 停止打字机效果
						
					}
				}
			},
			// 校对
			handleAIProofread() {
				let quill = this.$refs[this.refname].quill;
				const range = quill.getSelection()
				let that = this
				if(range.length<=range.index) {
					this.$message.error('请选中需要校对的内容')
					return false
				}
				let loading = this.$loading({
					lock: true,
					text: 'AI校对中...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				var div = document.createElement('div')
				div.innerHTML = this.value
				var text = quill.getText(range.index, range.length) + ' 请帮我校对一下这段文字的内容，并只返回校对的内容'
				this.$http.post('deepseek/askai', {
					ask: `${text}`,
				}).then(res => {
					if (loading) loading.close()
					quill.deleteText(range.index, range.length)
					that.currentIndex = 0
					that.range = range
					typeText(res.data.data)
				});
				
				function typeText(text) {
					let quill = that.$refs[that.refname].quill;
					if (that.currentIndex < text.length) {
						quill.insertText(that.range.index + that.currentIndex, text[that.currentIndex]);
						that.currentIndex++;
						that.typingInterval = setTimeout(()=>{
							typeText(text)
						}, 50); // 打字速度，单位毫秒
					} else {
						clearInterval(that.typingInterval); // 停止打字机效果
						
					}
				}
			},
			// 翻译
			handleAITranslation() {
				let quill = this.$refs[this.refname].quill;
				const range = quill.getSelection()
				let that = this
				if(range.length<=range.index) {
					this.$message.error('请选中需要翻译的内容')
					return false
				}
				let loading = this.$loading({
					lock: true,
					text: 'AI翻译中...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				var div = document.createElement('div')
				div.innerHTML = this.value
				var text = quill.getText(range.index, range.length) + ' 请帮我翻译一下这段文字的内容，并只返回翻译内容'
				this.$http.post('deepseek/askai', {
					ask: `${text}`,
				}).then(res => {
					if (loading) loading.close()
					quill.deleteText(range.index, range.length)
					that.currentIndex = 0
					that.range = range
					typeText(res.data.data)
				});
				
				function typeText(text) {
					let quill = that.$refs[that.refname].quill;
					if (that.currentIndex < text.length) {
						quill.insertText(that.range.index + that.currentIndex, text[that.currentIndex]);
						that.currentIndex++;
						that.typingInterval = setTimeout(()=>{
							typeText(text)
						}, 50); // 打字速度，单位毫秒
					} else {
						clearInterval(that.typingInterval); // 停止打字机效果
						
					}
				}
			},
			onEditorBlur() {
				//失去焦点事件
			},
			onEditorFocus() {
				//获得焦点事件
			},
			onEditorChange() {
				//内容改变事件
				this.$emit("input", this.value);
			},
			// 富文本图片上传前
			beforeUpload(file) {
				if (!file || !file.type) {
					this.$message.error("无法识别文件类型");
					return false;
				}
				if (this.uploadEmbedType === "file") {
					this.quillUpdateImg = true;
					return true;
				}
				const isVideoUpload = this.uploadEmbedType === "video";
				const isValidType = isVideoUpload ? file.type.startsWith("video/") : file.type.startsWith("image/");
				if (!isValidType) {
					this.$message.error(isVideoUpload ? "请选择视频类型文件" : "请选择图片类型文件");
					return false;
				}
				// 显示loading动画
				this.quillUpdateImg = true;
				return true;
			},

			uploadSuccess(res, file) {
				// res为图片服务器返回的数据
				// 获取富文本组件实例
				let quill = this.$refs[this.refname].quill;
				// 如果上传成功
				if (res.code === 0) {
					// 获取光标所在位置
					const selection = quill.getSelection();
					let length = selection ? selection.index : quill.getLength();
					const fileUrl = this.$fileUrl(res.file);
					if (this.uploadEmbedType === "file") {
						const fileName = file && file.name ? file.name : (res.file || "下载文件");
						const safeFileName = String(fileName).replace(/"/g, "&quot;");
						const safeFileUrl = String(fileUrl).replace(/"/g, "&quot;");
						quill.clipboard.dangerouslyPasteHTML(
							length,
							`<a href="${safeFileUrl}" download="${safeFileName}" target="_blank" rel="noopener noreferrer">${safeFileName}</a>`
						);
					} else {
						// 按按钮类型插入图片或视频
						quill.insertEmbed(length, this.uploadEmbedType, fileUrl);
					}
					// 调整光标到最后
					quill.setSelection(length + 1);
				} else {
					this.$message.error(this.uploadEmbedType === "video" ? "视频插入失败" : (this.uploadEmbedType === "file" ? "文件插入失败" : "图片插入失败"));
				}
				// loading动画消失
				this.quillUpdateImg = false;
				this.uploadEmbedType = "image";
			},
			// 富文本图片上传失败
			uploadError() {
				// loading动画消失
				this.quillUpdateImg = false;
				this.$message.error(this.uploadEmbedType === "video" ? "视频插入失败" : (this.uploadEmbedType === "file" ? "文件插入失败" : "图片插入失败"));
				this.uploadEmbedType = "image";
			}
		}
	};
</script>

<style>
	.editor .ql-toolbar .ql-table {
		width: auto !important;
		padding: 0 6px !important;
		font-size: 12px;
	}
	.editor .ql-toolbar .ql-table::before {
		content: "表格";
	}
	.editor .ql-toolbar .ql-file {
		width: auto !important;
		padding: 0 6px !important;
		font-size: 12px;
	}
	.editor .ql-toolbar .ql-file::before {
		content: "文件";
	}
	.editor .ql-editor .ql-video {
		display: block;
		max-width: 100%;
		min-width: 240px;
		min-height: 135px;
		resize: both;
		overflow: auto;
	}
	.editor {
		line-height: normal !important;
	}

	.ql-snow .ql-tooltip[data-mode="link"]::before {
		content: "请输入链接地址:";
	}

	.ql-snow .ql-tooltip.ql-editing a.ql-action::after {
		border-right: 0px;
		content: "保存";
		padding-right: 0px;
	}

	.ql-snow .ql-tooltip[data-mode="video"]::before {
		content: "请输入视频地址:";
	}

	.ql-container {
		/* height: 400px; */
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item::before {
		content: "14px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="small"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="small"]::before {
		content: "10px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="large"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="large"]::before {
		content: "18px";
	}

	.ql-snow .ql-picker.ql-size .ql-picker-label[data-value="huge"]::before,
	.ql-snow .ql-picker.ql-size .ql-picker-item[data-value="huge"]::before {
		content: "32px";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item::before {
		content: "文本";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="1"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="1"]::before {
		content: "标题1";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="2"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="2"]::before {
		content: "标题2";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="3"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="3"]::before {
		content: "标题3";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="4"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="4"]::before {
		content: "标题4";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="5"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="5"]::before {
		content: "标题5";
	}

	.ql-snow .ql-picker.ql-header .ql-picker-label[data-value="6"]::before,
	.ql-snow .ql-picker.ql-header .ql-picker-item[data-value="6"]::before {
		content: "标题6";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item::before {
		content: "标准字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="serif"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="serif"]::before {
		content: "衬线字体";
	}

	.ql-snow .ql-picker.ql-font .ql-picker-label[data-value="monospace"]::before,
	.ql-snow .ql-picker.ql-font .ql-picker-item[data-value="monospace"]::before {
		content: "等宽字体";
	}
</style>
