<template>
  <v-container>
    <h2>리워드 구매</h2>
    <h4>{{reward.title}}</h4>
    <p>{{reward.content}}</p>

    <p>구매정보입력</p>

    <v-btn>새 주소 입력</v-btn>
    <v-row class="justify-center">
      <v-col class="py-0 mt-3" sm="3">
        <label for="postcode">우편번호</label>
        <v-text-field
          v-model="postcode"
          outlined
          hide-details="true"
          id="postcode"
          required
        ></v-text-field>
      </v-col>
      <v-col class="my-2" sm="3">
        <v-btn @click="execDaumPostcode" dark>우편번호 찾기</v-btn>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="address">주소</label>
        <v-text-field
          type="text"
          v-model="address"
          outlined
          hide-details="true"
          id="address"
          required
        ></v-text-field>
      </v-col>
    </v-row>
    <v-row class="justify-center">
      <v-col class="py-0" sm="6">
        <label for="extraAddress">상세주소</label>
        <v-text-field
          v-model="extraAddress"
          id="extraAddress"
          type="text"
          hide-details="true"
          outlined
          required
        ></v-text-field>
      </v-col>
    </v-row>

  </v-container>
</template>

<script>
import { mapState } from "vuex";
import axios from "axios";

export default {
  created() {
    // getUserInfo();
    axios
      .get("리워즈 찾아주는 URL" + this.$route.params.id)
      .then(res => {
        this.reward = res.data;
      })
      .error(err => console.error(err));
  },

  methods: {
    // ...mapActions(["getUserInfo"]),

    execDaumPostcode() {
      const currentScroll = Math.max(
        document.body.scrollTop,
        document.documentElement.scrollTop
      );
      // eslint-disable-next-line
      new daum.Postcode({
        onComplete: data => {
          if (data.userSelectedType === "R") {
            this.address = data.roadAddress;
          } else {
            this.address = data.jibunAddress;
          }
          if (data.userSelectedType === "R") {
            if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
              this.extraAddress += data.bname;
            }
            if (data.buildingName !== "" && data.apartment === "Y") {
              this.extraAddress +=
                this.extraAddress !== ""
                  ? `, ${data.buildingName}`
                  : data.buildingName;
            }
            if (this.extraAddress !== "") {
              this.extraAddress = ` (${this.extraAddress})`;
            }
          } else {
            this.extraAddress = "";
          }
          this.postcode = data.zonecode;
          this.$refs.extraAddress.focus();
          this.searchWindow.display = "none";
          document.body.scrollTop = currentScroll;
        },
        onResize: size => {
          this.searchWindow.height = `${size.height}px`;
        },
        width: "100%",
        height: "100%"
      }).embed(this.$refs.searchWindow);
      this.searchWindow.display = "block";
    }
  },
  computed: {
    ...mapState(["user"])
  }
};
</script>

<style>
</style>