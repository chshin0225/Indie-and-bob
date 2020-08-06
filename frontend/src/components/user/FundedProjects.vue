<template>
  <v-container>
    <h2 class="text-center">펀딩한 프로젝트들</h2>
    <p>{{ fundedProjectList }}</p>
  </v-container>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'FundedProjects',

  computed: {
    ...mapState(['fundedProjectList',])
  },

  methods: {
    ...mapActions(['fetchFundedProjects',])
  },

  created() {
    this.fetchFundedProjects(this.$route.params.username)
  },
}
</script>

<style>

</style>