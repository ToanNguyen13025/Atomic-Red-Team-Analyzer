<template>
  <div>
    <button class="btn btn-outline-info" @click="fetchTechniques">Fetch data</button>
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
          Technique Tests
        </button>
      </li>
    </ol>

    <!-- Technique Tests Modal -->
    <div class="modal fade" id="testsModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-scrollable modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">Technique Tests - {{ selectedTechnique?.display_name }}</h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <ol class="list-group">
              <li
                v-for="test in selectedTechnique?.atomic_tests || []"
                :key="test.name"
                class="list-group-item d-flex justify-content-between align-items-start"
              >
                <div class="ms-2 me-auto">
                  <div class="fw-bold">{{ test.name }}</div>
                  {{ test.description }}
                </div>
                <button class="btn btn-primary" @click="openTestModal(test)">Test Details</button>
              </li>
            </ol>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>

    <!-- Test Detail Modal -->
    <div class="modal fade" id="detailModal" tabindex="-1">
      <div class="modal-dialog modal-dialog-scrollable modal-xl">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title">{{ selectedTest?.name }}</h5>
            <button class="btn-close" data-bs-dismiss="modal"></button>
          </div>
          <div class="modal-body">
            <p><strong>Description:</strong> {{ selectedTest?.description }}</p>
            <p><strong>Auto generated Guid: </strong>{{ selectedTest?.auto_generated_guid }}</p>
            <p><Strong>Supported platforms:</Strong></p>
            <ul>
              <li v-for="platform in selectedTest?.supported_platforms" :key="platform">
                {{ platform }}
              </li>
            </ul>
            <p><Strong>Input arguments:</Strong></p>
            <ul>
              <li v-for="(input, index) in selectedTest?.input_arguments" :key="(input, index)">
                <p>
                  <u>{{ index }}:</u>
                </p>
                <p>Description: {{ input?.description }}</p>
                <p>Type: {{ input?.type }}</p>
                <p>Default: {{ input?.defaultValue }}</p>
                <br />
              </li>
            </ul>
            <p><strong>Dependencies:</strong></p>
            <ul v-if="selectedTest?.dependencies?.length">
              <li v-for="(dependency, index) in selectedTest.dependencies" :key="index">
                <p><strong>Description:</strong> {{ dependency.description }}</p>

                <p><u>Prereq Command:</u></p>
                <ul>
                  <li v-for="(cmd, i) in splitLines(dependency.prereq_command)" :key="'pre-' + i">
                    {{ cmd }}
                  </li>
                </ul>
                <br />
                <p><u>Get Prereq Command:</u></p>
                <ul>
                  <li
                    v-for="(cmd, i) in splitLines(dependency.get_prereq_command)"
                    :key="'get-' + i"
                  >
                    {{ cmd }}
                  </li>
                </ul>
                <br />
              </li>
            </ul>
            <p><strong>Executor:</strong> {{ selectedTest?.executor?.name }}</p>
            <ul>
              <li>
                <p><u>Elevation required:</u> {{ selectedTest?.executor?.elevation_required }}</p>
              </li>
              <li>
                <p><u>Command:</u></p>
                <ul>
                  <li
                    v-for="(cmd, i) in splitLines(selectedTest?.executor?.command)"
                    :key="'pre-' + i"
                  >
                    {{ cmd }}
                  </li>
                </ul>
                <br />
                <p><u>Cleanup Command:</u></p>
                <ul>
                  <li
                    v-for="(cmd, i) in splitLines(selectedTest?.executor?.cleanup_command)"
                    :key="'get-' + i"
                  >
                    {{ cmd }}
                  </li>
                </ul>
              </li>
            </ul>

            <p><strong>Clean up command: </strong>{{ selectedTest?.executor?.cleanup_command }}</p>
            <p><strong>Command: </strong>{{ selectedTest?.executor?.command }}</p>
            <p><strong>Steps:</strong></p>
            <pre>{{ selectedTest?.executor?.steps }}</pre>
          </div>
          <div class="modal-footer">
            <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import * as bootstrap from 'bootstrap'

export default {
  name: 'TechniqueList',
  data() {
    return {
      attack_techniques: [],
      selectedTechnique: null,
      selectedTest: null,
    }
  },
  mounted() {
    this.attack_techniques = JSON.parse(localStorage.getItem('attack_techniques')) || []
  },
  methods: {
    splitLines(commandStr) {
      if (!commandStr) return []
      return commandStr.split(/\r?\n/).filter((line) => line.trim() !== '')
    },
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
    openTechniqueModal(technique) {
      this.selectedTechnique = technique
      const modal = new bootstrap.Modal(document.getElementById('testsModal'))
      modal.show()
    },
    openTestModal(test) {
      this.selectedTest = test
      const modal = new bootstrap.Modal(document.getElementById('detailModal'))
      modal.show()
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
