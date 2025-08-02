<script setup>
import TechniqueDetail from './TechniqueDetail.vue'
</script>
<template>
  <nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
      <a class="navbar-brand" href="#">Atomic RedTeam Analyzer</a>
      <button
        class="navbar-toggler"
        type="button"
        data-bs-toggle="collapse"
        data-bs-target="#navbarSupportedContent"
        aria-controls="navbarSupportedContent"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
          <router-link class="nav-link" to="/">Home</router-link>
          <router-link class="nav-link" to="/technique">Technique</router-link>
          <router-link class="nav-link" to="/mitre">Mitre</router-link>
          <router-link class="nav-link" to="/analyzer">Analyzer</router-link>

          <li class="nav-item dropdown">
            <a
              class="nav-link dropdown-toggle"
              href="#"
              role="button"
              data-bs-toggle="dropdown"
              aria-expanded="false"
            >
              Dropdown
            </a>
            <ul class="dropdown-menu">
              <li><a class="dropdown-item" href="#">Action</a></li>
              <li><a class="dropdown-item" href="#">Another action</a></li>
              <li><hr class="dropdown-divider" /></li>
              <li><a class="dropdown-item" href="#">Something else here</a></li>
            </ul>
          </li>
        </ul>
        <form class="d-flex" role="search" @submit.prevent="onSearch">
          <input
            v-model="searchValue"
            class="form-control me-2"
            placeholder="Search"
            aria-label="Search"
          />
          <button class="btn btn-outline-success" type="submit">Search</button>
        </form>
      </div>
    </div>
  </nav>
  <TechniqueDetail :technique="search_technique" v-model:show="showModal"> </TechniqueDetail>
</template>

<script>
export default {
  name: 'NavBar',
  data() {
    return {
      attack_techniques: [],
      search_technique: null,
      showModal: false,
      found: false,
    }
  },
  mounted() {
    this.attack_techniques = JSON.parse(localStorage.getItem('attack_techniques')) || []
  },
  methods: {
    onSearch() {
      const found = this.attack_techniques.find(
        (tech) => tech.attack_technique.toLowerCase() === this.searchValue.trim().toLowerCase(),
      )
      if (found) {
        this.search_technique = found
        this.showModal = true
      } else {
        alert("Can't find technique: " + this.searchValue)
      }
    },
  },
}
</script>
