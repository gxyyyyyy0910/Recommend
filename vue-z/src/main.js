import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

var axios = require('axios')
axios.default.baseURL = 'http://localhost:9090/api'
axios.default.withCredentials = true
Vue.prototype.$axios = axios
Vue.config.productionTip = false


Vue.use(ElementUI)

router.beforeEach((to, from, next) => {

    if (to.meta.requireAuth) {
        if (window.localStorage.getItem('user' || '[]')) {
            next();
        }
        else {
            router.push('login')
        }
    }
    else {
        next();
    }
})


new Vue({
    el: '#app',
    render: h => h(App),
    router,
    store,
    components: { App },
    template: '<App/>'
})

