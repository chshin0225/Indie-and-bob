<template>
  <v-container>

    <!-- <p>{{ projectList }}</p> -->
    <h1 class="text-center">Projects</h1>

    <!-- projects  -->
    <div v-for="project in projectList" :key="project.gameId">
      <v-card outlined :to="`/game/${project.gameId}`">
        <v-list-item three-line>
          <v-list-item-content>
            <v-list-item-title class="headline mb-1">{{ project.name }}</v-list-item-title>
            <v-list-item-subtitle>{{ project.deadline }}까지</v-list-item-subtitle>
            <v-list-item-subtitle>목표액: {{ project.aim }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-card>

    </div>

  </v-container>
</template>

<script>
import { mapState, mapActions } from "vuex";

export default {
  name: "GameMain",

  computed: {
    ...mapState(["projectList"])
  },

  methods: {
    ...mapActions(["fetchProjects"])
  },

  created() {
    this.fetchProjects();
  }
};
</script>

<style>
</style>