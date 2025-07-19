import './assets/main.css'

import { createApp } from 'vue'
import App from './App.vue'

// Bootstrap CSS & JS (bundle includes Popper)
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import 'bootstrap/dist/js/bootstrap.bundle'

import router from './router'

createApp(App).use(router).mount('#app')
