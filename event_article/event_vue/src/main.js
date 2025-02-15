import { createApp } from 'vue'
import './assets/main.scss'
import App from './app.vue'
import router from './router/router.js'
import { createPinia } from 'pinia'
import { createPersistedState } from 'pinia-persistedstate-plugin'



// 引入element-plus
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
const app = createApp(App)
const pinia = createPinia()
const persist = createPersistedState()


pinia.use(persist)
app.use(pinia)
app.use(ElementPlus)
app.use(router)
app.mount('#app')
