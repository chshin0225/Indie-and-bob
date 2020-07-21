<template>
  <v-container>

    <!-- article header -->
    <v-row>
      <v-col>
        <h1>Lorem ipsum dolor sit amet</h1>
      </v-col>
      <v-col class="d-flex justify-end align-end">
        <p class="mb-0">name of writer</p>
      </v-col>
    </v-row>
    <hr>

    <!-- article content -->
    <v-row>
      <v-col>
        <p>
          Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec nec leo sed arcu mattis mollis sodales ac neque. Interdum et malesuada fames ac ante ipsum primis in faucibus. Aliquam vel pharetra leo, sed placerat mi. Curabitur nec pulvinar orci, nec luctus urna. Sed porttitor tempus lacus, sit amet vulputate est pulvinar in. Vestibulum condimentum nibh mi, vel pulvinar ligula fermentum non. Donec sollicitudin aliquet tempor. Cras egestas egestas metus, ut iaculis nisl vestibulum non. Nullam euismod tortor in quam commodo sollicitudin.
          Sed semper sapien a purus tempor, ut dapibus eros bibendum. In tristique lectus pretium nisi imperdiet, a sodales risus cursus. Duis tristique nisl vitae pretium malesuada. Curabitur eu tincidunt mauris. Donec et tincidunt magna. Morbi viverra lorem eget dui lobortis laoreet. Donec pretium ipsum sed laoreet gravida. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Suspendisse sodales turpis vel sapien vehicula, vel consequat mauris feugiat. Nunc auctor dolor molestie cursus viverra. Donec tincidunt, lacus a ullamcorper fermentum, turpis ex viverra velit, et condimentum massa erat non nisl. Mauris ipsum lorem, vulputate ac elementum in, placerat suscipit quam. Cras ac tincidunt urna. Aenean nibh nisi, semper non purus a, sagittis dignissim libero. Curabitur et nisi sollicitudin, semper neque id, interdum enim. Duis tempus ut massa ut varius.
        </p>
      </v-col>
    </v-row>

    <!-- buttons -->
    <v-row>
      <v-col>
        <v-btn class="mr-2" color="accent" depressed>Edit</v-btn>
        <v-btn color="secondary" depressed>Delete</v-btn>
      </v-col>
    </v-row>
    <hr>

    <v-row>
      <v-col>
        <h4>Comments</h4>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'CommunityArticle',
}
</script>

<style>

</style>