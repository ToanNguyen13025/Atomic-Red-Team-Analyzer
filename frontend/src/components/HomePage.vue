<script setup>
import { ref, onMounted, watch } from 'vue'
import axios from 'axios'
import { Chart, Pie, Tooltip } from 'vue3-charts'

const data = ref([])
const width = 400
const coverage = ref(null)

onMounted(async () => {
  const cached = localStorage.getItem('coverage_data')

  if (cached) {
    try {
      coverage.value = JSON.parse(cached)
    } catch (err) {
      console.log(err)
      console.warn('Invalid JSON in localStorage, clearing it.')
      localStorage.removeItem('coverage_data')
    }
  }

  // Nếu chưa có hoặc lỗi parse => fetch mới
  if (!coverage.value) {
    try {
      const res = await axios.get('/api/atomic/techniques/analyze')
      coverage.value = res.data
    } catch (err) {
      console.error('Failed to fetch coverage data from API:', err)
    }
  }

  if (coverage.value) {
    const covered = coverage.value.atomic_techniques_count
    const uncovered = coverage.value.mitre_techniques_count - covered

    data.value = [
      { name: 'Covered', pl: covered, p2: coverage.value.Coverage },
      { name: 'Uncovered', pl: uncovered, p2: 100 - coverage.value.Coverage },
    ]
  }
})

// Tự động lưu lại vào localStorage khi coverage thay đổi
watch(
  coverage,
  (newVal) => {
    if (newVal) {
      localStorage.setItem('coverage_data', JSON.stringify(newVal))
    }
  },
  { deep: true },
)
</script>

<template>
  <main class="container mt-4">
    <div class="text-center mb-4">
      <h1>Welcome to Atomic RedTeam Analyzer</h1>
      <p>Analyze and visualize your Atomic Red Team tests.</p>
      <p>Get started by exploring the techniques or running your tests.</p>
    </div>

    <div class="text-center">
      <h4 class="mb-3">MITRE Coverage Chart</h4>
      <Chart
        direction="circular"
        :size="{ width, height: 400 }"
        :data="data"
        :margin="{ top: 20, right: 0, bottom: 20, left: 0 }"
        :axis="{ x: { hide: true }, y: { hide: true } }"
        :config="{ controlHover: false }"
      >
        <template #layers>
          <Pie :dataKeys="['name', 'pl']" :pie-style="{ innerRadius: 100, padAngle: 0.05 }" />
        </template>
        <template #widgets>
          <Tooltip
            :config="{
              name: {},
              pl: { label: 'Techniques' },
              p2: { label: 'Probability', format: (v) => v.toFixed(2) + '%' },
            }"
            hideLine
          />
        </template>
      </Chart>
    </div>
  </main>
</template>
<style>
svg text {
  display: none; /* Tắt tất cả text (nhãn trục) nếu cần */
}
line {
  stroke: transparent !important;
}
</style>
