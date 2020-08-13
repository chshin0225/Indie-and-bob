<template>
  <div>
    <v-card flat>
      <template v-for="project in projectList">

        <v-list-item three-line :key="project.gameId" :to="`/game/${project.gameId}`">
          <v-list-item-content class="py-0">
            <v-col cols="6">
              <v-list-item-title>{{ project.name }}</v-list-item-title>
              <v-list-item-subtitle>{{ $moment(project.deadline).format('YYYY.MM.DD') }}까지</v-list-item-subtitle>
              <v-list-item-subtitle>{{ project.genreName }}</v-list-item-subtitle>
            </v-col>
            <v-col cols="6">
              <v-img contain height="70px" :src="project.thumbnail"></v-img>
            </v-col>
          </v-list-item-content>
        </v-list-item>
        <v-divider :key="project.gameId"></v-divider>
      </template>
    </v-card>
  </div>
</template>

<script>
export default {
  name: 'RankedList',

  props: ['projectList'],
}
</script>

<style>

</style>