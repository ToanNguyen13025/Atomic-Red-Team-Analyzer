import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import MitrePage from '../components/MitrePage.vue'
import TechniquePage from '../components/TechniquePage.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/technique', component: TechniquePage },
  { path: '/mitre', component: MitrePage },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
