<template>
<v-expansion-panels>
    <v-expansion-panel
      v-for="question in questions"
      :key="question.id"
    >
      <v-expansion-panel-header>
          {{question.title}}
          {{question.content}}
      </v-expansion-panel-header>
      <v-expansion-panel-content
      v-if="question.answer">
      {{question.answer}}
      </v-expansion-panel-content>
      <v-expansion-panel-content
      v-else>
      </v-expansion-panel-content>

    </v-expansion-panel>
  </v-expansion-panels>
</template>

<script>
import axios from 'axios';
import SERVER from '../../api/base';
export default {
    name:'QuestionandAnswer',
    created() {
      axios.get(SERVER.BASE + "QuestionandAnswer")
      .then(res => {
          this.questions = res.data.questions
      })
      .catch(err =>
      console.error(err))
    },

}
</script>

<style>

</style>