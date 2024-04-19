import './assets/main.css'
import 'element-plus/dist/index.css'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import VueAxios from 'vue-axios'
import axios from 'axios'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(VueAxios, axios);
app.use(router)

app.mount('#app')

// 跳转后返回顶部
router.afterEach((to, from, next) => {
    window.scrollTo(0, 0);
})