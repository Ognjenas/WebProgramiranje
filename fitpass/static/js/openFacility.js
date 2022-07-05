Vue.component("open-facility", {
    data: function () {

        return {
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }

            },
            currentFacility: {location: {},
                openTime: {
                    startWorkingDays : {},
                    endWorkingDays : {},
                    startSaturday : {},
                    endSaturday : {},
                    startSunday: {},
                    endSunday : {}
                }
            },
            id: "",
            userInfo: {
                username: "",
                role: ""
            },
            offers: {}
        }
    },
    template: ` 
<div>
<div v-if="$cookies.get('token') != null">
        <label>Username: {{userInfo.username}}</label>
        <button v-on:click="editProfile">Profile</button>
    </div>
    <div v-if="$cookies.get('token') == null">
        <button v-on:click="login">Login</button>
    </div>
    <h1>SHOW FACILITY</h1>
    <table>
        <tr>
        <td><label>Name</label></td>
        <td><label>{{currentFacility.name}}</label></td>
        </tr>
        
        <tr>
        <td><label>Type</label></td>
        <td><label>{{currentFacility.type}}</label></td>
        </tr>
        
        <tr>
        <td><label>Image</label></td>
        <td><img :src="currentFacility.imgSource" width="100" height="100"></td>
        </tr>
        
        <tr>
        <td><label>Location</label></td>
        <td> 
            <p>Geo.Length:{{currentFacility.location.geoLength}} / Geo.Width:{{currentFacility.location.geoWidth}}</p>
		    <p>City:{{currentFacility.location.city}}-Street:{{currentFacility.location.street}}-Nr:{{currentFacility.location.strNumber}}-Postal:{{currentFacility.location.postalCode}}</p></td>
        </tr>
        
        <tr>
        <td><label>isOpen</label></td>
        <td><label>{{currentFacility.isOpen}}</label></td>
        </tr>
        
        <tr>
        <td><label>Average Grade</label></td>
        <td><label>{{currentFacility.averageGrade}}</label></td>
        </tr>
        
        <tr>
        <td><label>Working Hours</label></td>
        <td>
            <p>Monday-Friday: From {{currentFacility.openTime.startWorkingDays.hour}}:{{currentFacility.openTime.startWorkingDays.minute}} to {{currentFacility.openTime.endWorkingDays.hour}}:{{currentFacility.openTime.endWorkingDays.minute}}</p>
		    <p>Saturday: From {{currentFacility.openTime.startSaturday.hour}}:{{currentFacility.openTime.startSaturday.minute}} to {{currentFacility.openTime.endSaturday.hour}}:{{currentFacility.openTime.endSaturday.minute}}</p>
		    <p>Sunday: From {{currentFacility.openTime.startSunday.hour}}:{{currentFacility.openTime.startSunday.minute}} to {{currentFacility.openTime.endSunday.hour}}:{{currentFacility.openTime.endSunday.minute}}</p></td>
        </tr>
    </table>
    <button v-on:click="createOffer">Create offer</button>
    <div>
    <table>
	<tr>
	    <th></th>
		<th>Name</th>
		<th>Type</th>
	</tr>
		
	<tr v-for="offer in offers" >
		<td></td>
		<td>{{offer.name}}</td>
		<td>{{offer.type}}</td>
	</tr>
</table>
</div>
</div>		  
`
    ,
    methods:
        {
            login() {
                router.push('/login')
            },
            editProfile() {
                router.push("/edit-profile")
            },
            createOffer() {
                router.push("/open-facility/create-offer")
            }
        },

    mounted() {
        if ($cookies.get("token") != null) {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                    $cookies.set("userInfo", response.data, 10000)
                })
        }

        axios
            .get('/manager/get-facility', this.configHeaders)
            .then(response => {
                this.currentFacility = response.data;
            });

        axios.get('/manager/get-facility-offers', this.configHeaders)
            .then(response => {
            this.offers = response.data.offers;
        });
    },
});