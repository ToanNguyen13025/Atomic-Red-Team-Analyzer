<template>
  <button class="btn btn-outline-info" @click="fetchTechniques">Fetch data</button>
  <ol class="list-group list-group-numbered">
    <li
      v-for="technique in attack_techniques"
      :key="technique"
      class="list-group-item d-flex justify-content-between align-items-start"
    >
      <div class="ms-2 me-auto">
        <div class="fw-bold">{{ technique.attack_technique }}</div>
        {{ technique.display_name }}
      </div>
      <button
        type="button"
        class="btn btn-secondary"
        data-bs-container="body"
        data-bs-toggle="popover"
        data-bs-placement="left"
        data-bs-content="Left popover"
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
      attack_techniques: [],
    }
  },
  mounted() {
    this.attack_techniques = JSON.parse(localStorage.getItem('attack_techniques')) || []
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
        .get('/api/atomic/techniques')
        .then((response) => {
          this.attack_techniques = response.data
        })
        .catch((error) => {
          console.error('Error fetching techniques:', error)
        })
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
