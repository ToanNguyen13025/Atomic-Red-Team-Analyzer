<script setup>
import TechniqueDetail from './TechniqueDetail.vue'
</script>
<template>
  <div>
    <button class="btn btn-outline-info" @click="fetchTechniques">Fetch data</button>
    <button class="btn btn-outline-info" @click="download">Download data</button>
    <ol class="list-group list-group-numbered mt-3">
      <li
        v-for="technique in attack_techniques"
        :key="technique.attack_technique"
        class="list-group-item d-flex justify-content-between align-items-start"
      >
        <div class="ms-2 me-auto">
          <div class="fw-bold">{{ technique.attack_technique }}</div>
          {{ technique.display_name }}
        </div>
        <button class="btn btn-primary" @click="openTechniqueModal(technique)">
          Technique Detail
        </button>
      </li>
    </ol>
    <TechniqueDetail :technique="selectedTechnique" v-model:show="showModal" />
  </div>
</template>

<script>
import axios from 'axios'

export default {
  name: 'TechniqueList',
  data() {
    return {
      attack_techniques: [],
      selectedTechnique: null,
      selectedTest: null,
      showModal: false,
    }
  },
  mounted() {
    this.attack_techniques = JSON.parse(localStorage.getItem('attack_techniques')) || []
  },
  methods: {
    fetchTechniques() {
      axios
        .get('/api/atomic/techniques')
        .then((response) => {
          this.attack_techniques = response.data
        })
        .catch((error) => {
          console.error('Error fetching techniques:', error)
        })
    },
    download() {
      axios
        .get('/api/atomic/techniques/export', { responseType: 'blob' })
        .then((response) => {
          const blob = new Blob([response.data], {
            type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet',
          })
          const url = window.URL.createObjectURL(blob)
          const link = document.createElement('a')
          link.href = url
          link.setAttribute('download', 'techniques.xlsx')
          document.body.appendChild(link)
          link.click()
          document.body.removeChild(link)
          window.URL.revokeObjectURL(url)
        })
        .catch((error) => {
          console.error('❌ Error downloading Excel file:', error)
        })
    },
    openTechniqueModal(technique) {
      this.selectedTechnique = technique
      this.showModal = true
    },
  },
  watch: {
    attack_techniques: {
      handler(newVal) {
        localStorage.setItem('attack_techniques', JSON.stringify(newVal))
      },
      deep: true,
    },
  },
}
</script>
