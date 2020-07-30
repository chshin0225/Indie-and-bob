import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify';
import VueSimpleAlert from "vue-simple-alert";
import VueMoment from 'vue-moment'
import Clipboard from 'v-clipboard'

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount('#app')

Vue.use(VueSimpleAlert);
Vue.use(VueMoment);
Vue.use(Clipboard);
