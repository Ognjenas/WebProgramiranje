Vue.component("facility-show", {
    data: function () {

        return {
            configHeaders: {
                headers: {
                    token: $cookies.get("token"),
                }

            },
            currentFacility: null,
            id: "",
            userInfo: {
                username: "",
                role: ""
            },
            offers : "",
            reserveOffer: {date: {}, time: "", id: ""},
            dateValue: ""

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
    
    <h1>Offers</h1>
    <div v-for="offer in offers">
        <p>Name: {{offer.name}}</p>
        <p>Type: {{offer.type}}</p>
        <p>Duration: {{offer.duration}}</p>
        <button v-if="userInfo.role == 'CUSTOMER'" v-on:click="reserveButton(offer.id)">Reserve</button>
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
            reserveButton(id) {
                router.push("/facility-show/offer/"+id);
            }
        },

    mounted() {

        axios
            .get('/show-facility?id=' + this.$route.params.id)
            .then(response => {
                this.currentFacility = response.data;
        });

        if ($cookies.get("token") != null) {
            axios.post('users/get-info', $cookies.get("token"), this.configHeaders)
                .then(response => {
                    this.userInfo = response.data
                })
        }

        axios
            .get('/show-facility/offers?id=' + this.$route.params.id)
            .then(response => {
                this.offers = response.data.offers;
            })

    },
});