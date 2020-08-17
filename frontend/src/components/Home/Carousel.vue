<template>
    <v-carousel
    cycle
    height="400"
    hide-delimiter-background
    show-arrows-on-hover

  >
    <v-carousel-item
      v-for="project in projectList"
      :key="project.gameId"
    >
      <!-- <v-sheet
        height="100%"
      >
        <v-row
          class="fill-height"
          align="center"
          justify="center"
        >
          <div class="display-3">{{ project }} Slide</div>
        </v-row>
      </v-sheet> -->



      <v-row class="fill-height">
        <!-- <v-col cols=6> -->
          <v-img :src="project.thumbnail" contain></v-img>
        <!-- </v-col> -->
      </v-row>



    </v-carousel-item>
  </v-carousel>
</template>

<script>
export default {
  name: 'Carousel',

  props: ['projectList',],
}
</script>

<style scoped>

</style>