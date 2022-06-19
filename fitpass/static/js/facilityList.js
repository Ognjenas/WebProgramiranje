Vue.component("facility-list", {
    data: function () {

        return {
            facilitiesDto: null,
            form:{  name: "",
                    type:"",
                    city:"",
                    grade:""
                    },
            configHeaders : {
                headers: {
                    token: $cookies.get("token"),
                }
            },
            userInfo : {
                username : "",
                role : ""
            }
        }
    },
    template: ` 
<div>
    <label>Username: {{userInfo.username}}</label>
    <p>
    Search:</p>
    <input type="text" placeholder="Search for Facility"  v-model="form.name" v-on:change="searchFacility()" > 
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
	    <th></th>
		<th>Name</th>
		<th>Type</th>
		<th>Location</th>
		<th>Average Grade</th>
		<th>Working Hours</th>
	</tr>
		
	<tr v-for="f in facilitiesDto.allFacilities" >
		<td></td>
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
	</tr>
</table>
	<p>
		<a href="#/cnt">Contact</a>
	</p>
	<button class="login-button" v-on:click="logout">Odjavi se</button>
</div>		  
`
    ,
    methods :
        {
            searchFacility : function () {
                axios.get('facilities/search?name='+ this.form.name + '&type=' + this.form.type + '&city=' + this.form.city + '&grade=' + this.form.grade, this.configHeaders)
                    .then(response => {
                        if(response.status === 401) {
                            router.push("/");
                        }
                        this.facilitiesDto = response.data})

            },
            logout : function () {
                $cookies.remove('token')
                router.push('/login')
            }



    },

    mounted () {
        if($cookies.get("token") == null) {
            router.push("/login")
        }
            axios
                .get('/facilities/', this.configHeaders)
                .then(response =>{
                    if(response.status === 200) {
                        this.facilitiesDto = response.data
                    } else if (response.status === 401) {
                        router.push("/");
                    }

                })
            axios.post('users/get-info', $cookies.get("token"))
                .then(response => {
                    this.userInfo = response.data
                })

    },
});