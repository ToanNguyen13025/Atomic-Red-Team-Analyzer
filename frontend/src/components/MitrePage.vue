<template>
  <button class="btn btn-outline-info" @click="fetchTechniques">Fetch data</button>
  <ol class="list-group list-group-numbered">
    <li
      v-for="technique in mitre_techniques"
      :key="technique"
      class="list-group-item d-flex justify-content-between align-items-start"
    >
      <div class="ms-2 me-auto">
        <div class="fw-bold">{{ technique['technique'] }}</div>
        {{ technique['name'] }}
      </div>
      <button
        type="button"
        class="btn btn-secondary"
        data-bs-container="body"
        data-bs-toggle="popover"
        data-bs-placement="left"
        :data-bs-content="technique['description']"
      >
        Popover on left
      </button>
    </li>
  </ol>
</template>

<script>
import axios from 'axios'
import * as bootstrap from 'bootstrap'

export default {
  name: 'TechniqueList',
  data() {
    return {
      mitre_techniques: [],
    }
  },
  mounted() {
    this.mitre_techniques = JSON.parse(localStorage.getItem('mitre_techniques')) || []
    this.$nextTick(() => {
      const popoverTriggerList = [].slice.call(
        document.querySelectorAll('[data-bs-toggle="popover"]'),
      )
      popoverTriggerList.forEach(function (popoverTriggerEl) {
        new bootstrap.Popover(popoverTriggerEl)
      })
    })
  },
  methods: {
    fetchTechniques() {
      axios
        .get('/api/atomic/techniques/mitre')
        .then((response) => {
          this.mitre_techniques = response.data
        })
        .catch((error) => {
          console.error('Error fetching techniques:', error)
        })
    },
  },
  watch: {
    mitre_techniques: {
      handler(newVal) {
        localStorage.setItem('mitre_techniques', JSON.stringify(newVal))
      },
      deep: true,
    },
  },
}
</script>
