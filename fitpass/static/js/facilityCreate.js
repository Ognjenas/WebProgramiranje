Vue.component("facility-create", {
    data: function () {
        return {
            form:{
                name:null,
                type:null,
                city:null,
                street:null,
                strNum:null,
                postCode:null,
                geoWidth:null,
                geoLength:null,
            },
            selectedFile:null
        }
    },
    template: ` 
<div>
    <h1>OVO JE FACILITY CREATE</h1>
    <form method="post">
    <h3>Enter New Facility dates</h3>
    <label>Name</label>
        <input id="name" name="name" v-model="form.name" placeholder="Enter Facility Name">
    <label>Type</label>
    <select id="type" name="type" v-model="form.type" >
      <option value="">Select Facility Type</option>
      <option value="GYM">GYM</option>
      <option value="FIGHTING_GYM">FIGHTING_GYM</option>
      <option value="SWIMMING_POOL">SWIMMING_POOL</option>
      <option value="SPORTS_CENTER">SPORTS_CENTER</option>
      <option value="DANCE_STUDIO">DANCE_STUDIO</option>
      <option value="STADIUM">STADIUM</option>
    </select>
    <label>LOCATION</label>
    <input id="city" name="city" v-model="form.city" placeholder="Enter City Name">
    <input id="street" name="street" v-model="form.street" placeholder="Enter Street Name">
    <input id="strNum" name="strNum" v-model="form.strNum" placeholder="Enter Street Number">
    <input id="postCode" name="postCode" v-model="form.postCode" placeholder="Enter Postal Code">
    <input id="geoWidth" name="geoWidth" v-model="form.geoWidth" placeholder="Enter Geographical Width">
    <input id="geoLength" name="geoLength" v-model="form.geoLength" placeholder="Enter Geographical Length">
    <label>Select Image</label>
    <input type="file" v-on:change="fileSelected()" accept="image/*" >
    <button name="createFacilityButton" v-on:click="createFacility()" >CreateFacility</button>
    </form>
    
    <p>create-form:  |{{form.name}}|,|{{form.type}}|,|{{form.city}}|,|{{form.street}}| </p>
</div>		  
`
    ,
    methods : {
        createFacility : function () {
            console.log(this.form);
            axios.post('/facilities/create',this.form)
        },

        fileSelected : function(event) {
            this.selectedFile=event.target.files[0];
        }

    },

    mounted () {
        if($cookies.get("token") == null) {
            router.push("/login")
        }
    },
});