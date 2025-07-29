import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '../components/HomePage.vue'
import MitrePage from '../components/MitrePage.vue'
import TechniquePage from '../components/TechniquePage.vue'
import AnalyzerPage from '../components/AnalyzerPage.vue'

const routes = [
  { path: '/', component: HomePage },
  { path: '/technique', component: TechniquePage },
  { path: '/mitre', component: MitrePage },
  { path: '/analyzer', component: AnalyzerPage },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
