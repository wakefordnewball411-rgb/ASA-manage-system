import { createApp } from 'vue'          //导入vue方法
import { createPinia } from 'pinia'      //导入pinia
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'   //pinia持久化
//导入跟组件和路由
import App from './App.vue'
import router from './router'
//UI组件库
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import zhCn from 'element-plus/es/locale/lang/zh-cn'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'  //element图标
import '@/assets/styles/global.css'   //全局样式

const app = createApp(App)

const pinia = createPinia()
pinia.use(piniaPluginPersistedstate)

//全局注册element plus图标
for(const [key,component] of Object.entries(ElementPlusIconsVue)){
    app.component(key,component)
}

//挂载所有核心插件到vue实例
app.use(pinia)
app.use(router)
app.use(ElementPlus,{
    locale:zhCn,
    size:'default'
})

app.mount('#app')

