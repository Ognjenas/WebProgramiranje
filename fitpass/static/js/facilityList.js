Vue.component("facility-list", {
    data: function () {

        return {
            facilityList: "",
            sortDirection: null,
            sortIndex: null,
            form: {
                name: "",
                type: "",
                city: "",
                grade: ""
            },
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            userInfo: {
                username: "",
                role: ""
            }
        }
    },
    template: ` 
<div>
    <label>Username: {{userInfo.username}}</label>
    <p>
    Search:</p>
    
    <input type="text" placeholder="Search for Facility"  v-model="form.name" v-on:input="searchFacility()" > 
    <select name="type" v-model="form.type" v-on:change="searchFacility()">
      <option value="">Select Type</option>
      <option value="GYM">GYM</option>
      <option value="FIGHTING_GYM">FIGHTING_GYM</option>
      <option value="SWIMMING_POOL">SWIMMING_POOL</option>
      <option value="SPORTS_CENTER">SPORTS_CENTER</option>
      <option value="DANCE_STUDIO">DANCE_STUDIO</option>
      <option value="STADIUM">STADIUM</option>
    </select>
    <select name="city" v-model="form.city" v-on:change="searchFacility()" >
      <option value="">Select City</option>
      <option value="Novi Sad">NS</option>
      <option value="Beograd">BG</option>
      <option value="Apatin">APA</option>
    </select>
    <select name="grade" v-model="form.grade" v-on:change="searchFacility()" >
      <option value="">Average Grade</option>
      <option value="0-1"><1</option>
      <option value="1-2">1-2</option>
      <option value="2-3">2-3</option>
      <option value="3-4">3-4</option>
      <option value="4-5">>4</option>
    </select>
    <p>search-form:  |{{form.name}}|,|{{form.type}}|,|{{form.city}}|,|{{form.grade}}| </p>
    </p>
	Available Facilities:
	<table>
	<tr>
		<th v-on:click="sortList(0)">Name</th>
		<th>Type</th>
		<th v-on:click="sortList(1)">Location</th>
		<th v-on:click="sortList(2)">Average Grade</th>
		<th>Working Hours</th>
		<th>Image</th>
	</tr>
		
	<tr v-for="f in facilityList">
		<td>{{f.name}}</td>
		<td>{{f.type}}</td>
		<td>
		    <p>Geo.Length:{{f.location.geoLength}} / Geo.Width:{{f.location.geoWidth}}</p>
		    <p>City:{{f.location.city}}-Street:{{f.location.street}}-Nr:{{f.location.strNumber}}-Postal:{{f.location.postalCode}}</p>
		</td>
		<td>{{f.averageGrade}}</td>
		<td>
		    <p>Monday-Friday: From {{f.openTime.startWorkingDays.hour}}:{{f.openTime.startWorkingDays.minute}} to {{f.openTime.endWorkingDays.hour}}:{{f.openTime.endWorkingDays.minute}}</p>
		    <p>Saturday: From {{f.openTime.startSaturday.hour}}:{{f.openTime.startSaturday.minute}} to {{f.openTime.endSaturday.hour}}:{{f.openTime.endSaturday.minute}}</p>
		    <p>Sunday: From {{f.openTime.startSunday.hour}}:{{f.openTime.startSunday.minute}} to {{f.openTime.endSunday.hour}}:{{f.openTime.endSunday.minute}}</p>
		</td>
		<td>
		    <img :src="f.imgSource" width="100" height="100">
        </td>
	</tr>
</table>
	<button class="login-button" v-on:click="logout">Odjavi se</button>
	<button v-if="userInfo.role == 'ADMINISTRATOR'" class="login-button" v-on:click="listUsers">Svi korisnici</button>
</div>		  
`
    ,
    methods:
        {
            searchFacility: function () {
                axios.get('facilities/search?name=' + this.form.name + '&type=' + this.form.type + '&city=' + this.form.city + '&grade=' + this.form.grade, this.configHeaders)
                    .then(response => {
                        this.facilityList = response.data.allFacilities;
                    })
                this.sortIndex=null;
                this.sortDirection=null;

            },

            logout: function () {
                $cookies.remove('token')
                router.push('/login')
            },
            listUsers : function () {
                router.push('/users-list')
            },


            sortList(indexCol) {
                if(this.sortIndex===indexCol) {
                    if (this.sortDirection === "desc") {
                        this.sortDirection = "asc";
                        this.sorting(indexCol);
                    } else {
                        this.sortDirection = "desc";
                        this.sorting(indexCol);
                    }
                }else{
                    this.sortIndex=indexCol;
                    this.sortDirection = "asc";
                    this.sorting(indexCol);
                }
            },


            sorting(indexCol){
                console.log(indexCol);
                console.log(this.sortDirection);
                if (indexCol===0) {
                    if(this.sortDirection==="asc") {
                        this.facilityList.sort((a, b) => a.name > b.name ? 1 : -1);
                    }else{
                        this.facilityList.sort((a, b) => a.name < b.name ? 1 : -1);
                    }
                } else if (indexCol===1){
                    if(this.sortDirection==="asc") {
                        this.facilityList.sort((a, b) => a.location.city > b.location.city ? 1 : -1);
                    }else{
                        this.facilityList.sort((a, b) => a.location.city < b.location.city ? 1 : -1);
                    }
                } else if (indexCol===2){
                    if(this.sortDirection==="asc") {
                        this.facilityList.sort((a, b) => a.averageGrade > b.averageGrade ? 1 : -1);
                    }else{
                        this.facilityList.sort((a, b) => a.averageGrade < b.averageGrade ? 1 : -1);
                    }
                }
            },


        },

    mounted() {
        if ($cookies.get("token") == null) {
            router.push("/login")
        }
        axios
            .get('/facilities/', this.configHeaders)
            .then(response => {
                this.facilityList = response.data.allFacilities;
            })
        axios.post('users/get-info', $cookies.get("token"))
            .then(response => {
                this.userInfo = response.data
            })

    },
});