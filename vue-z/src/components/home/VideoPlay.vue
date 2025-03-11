<template>
    <div class="video-container" >
      <video id="v1" autoplay loop muted poster="../../assets/images/home-page-content.png">
        <source src="../../assets/home.mp4" type="video/mp4"/>
      </video>
    </div>
</template>

<script>

export default {
  name: "VideoPlay"
}
</script>

<style scoped>

    video{
      position: fixed;
      object-fit: fill;
      right: 0;
      bottom: 0;
      /*
      height: auto;
      width: auto;*/
      height: 100%;
      width: 100%;
      min-height: 100%;
      min-width: 100%;
      z-index: -100;
      background-size: cover;
      display: block;
    }
    source{
      min-width: 100%;
      min-height: 100%;
      height: 100%;
      width: 100%;
    }
</style>