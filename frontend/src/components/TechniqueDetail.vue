<template>
  <!-- Modal 1: Technique Detail + Test List -->
  <div class="modal fade" id="techniqueModal" tabindex="-1" ref="techniqueModalRef">
    <div class="modal-dialog modal-xl modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">
            Technique Tests - {{ technique?.attack_technique }} - {{ technique?.display_name }}
          </h5>
          <button class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <ol class="list-group">
            <li
              v-for="test in technique?.atomic_tests || []"
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

  <!-- Modal 2: Test Detail -->
  <div class="modal fade" id="testDetailModal" tabindex="-1" ref="testModalRef">
    <div class="modal-dialog modal-xl modal-dialog-scrollable">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title">{{ selectedTest?.name }}</h5>
          <button class="btn-close" data-bs-dismiss="modal"></button>
        </div>
        <div class="modal-body">
          <p><strong>Description:</strong> {{ selectedTest?.description }}</p>
          <p><strong>Auto generated Guid: </strong>{{ selectedTest?.auto_generated_guid }}</p>

          <p><strong>Supported platforms:</strong></p>
          <ul>
            <li v-for="platform in selectedTest?.supported_platforms" :key="platform">
              {{ platform }}
            </li>
          </ul>

          <p><strong>Input arguments:</strong></p>
          <ul>
            <li v-for="(input, key) in selectedTest?.input_arguments" :key="key">
              <p>
                <u>{{ key }}</u>
              </p>
              <p>Description: {{ input?.description }}</p>
              <p>Type: {{ input?.type }}</p>
              <p>Default: {{ input?.default }}</p>
              <br />
            </li>
          </ul>

          <p><strong>Dependencies:</strong></p>
          <ul v-if="selectedTest?.dependencies?.length">
            <li v-for="(dep, i) in selectedTest.dependencies" :key="i">
              <p><strong>Description:</strong> {{ dep.description }}</p>
              <p><u>Prereq Command:</u></p>
              <ul>
                <li v-for="(cmd, i) in splitLines(dep.prereq_command)" :key="'pre-' + i">
                  {{ cmd }}
                </li>
              </ul>
              <p><u>Get Prereq Command:</u></p>
              <ul>
                <li v-for="(cmd, i) in splitLines(dep.get_prereq_command)" :key="'get-' + i">
                  {{ cmd }}
                </li>
              </ul>
              <br />
            </li>
          </ul>

          <p><strong>Executor:</strong> {{ selectedTest?.executor?.name }}</p>
          <ul>
            <li><u>Elevation required:</u> {{ selectedTest?.executor?.elevation_required }}</li>
            <li>
              <p><u>Command:</u></p>
              <ul>
                <li
                  v-for="(cmd, i) in splitLines(selectedTest?.executor?.command)"
                  :key="'cmd-' + i"
                >
                  {{ cmd }}
                </li>
              </ul>
              <p><u>Cleanup Command:</u></p>
              <ul>
                <li
                  v-for="(cmd, i) in splitLines(selectedTest?.executor?.cleanup_command)"
                  :key="'clean-' + i"
                >
                  {{ cmd }}
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <div class="modal-footer">
          <button class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onMounted, ref, watch } from 'vue'
import * as bootstrap from 'bootstrap'

const props = defineProps({
  technique: Object,
  show: Boolean,
})
const emit = defineEmits(['update:show'])

const selectedTest = ref(null)

const techniqueModalRef = ref(null)
const testModalRef = ref(null)

let techniqueModal, testModal

onMounted(() => {
  techniqueModal = new bootstrap.Modal(techniqueModalRef.value)
  testModal = new bootstrap.Modal(testModalRef.value)

  watch(
    () => props.show,
    (val) => {
      if (val) techniqueModal.show()
      else techniqueModal.hide()
    },
  )

  techniqueModalRef.value.addEventListener('hidden.bs.modal', () => {
    emit('update:show', false)
  })
})

function openTestModal(test) {
  selectedTest.value = test
  testModal.show()
}

function splitLines(str) {
  return str?.split(/\r?\n/).filter((l) => l.trim() !== '') ?? []
}
</script>
