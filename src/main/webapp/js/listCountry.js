fetch("/Projekt_M133_Military_ranking-1.0/resource/country/list")
    .then((response) => {
        if (response.status == 403) {
            document.querySelector("#countries").innerHTML = "";
            document.querySelector("#message").innerHTML =
                "You are not authorized to view this page.";
            throw new Error("Authorization failed");
        }
        return response.json();
    })
    .then((countries) => {
        const countryList = document.querySelector("#countries");
        countries.forEach((country) => {
            const row = document.createElement("tr");
            row.innerHTML = `
					<td>${country.countryID}</td>
					<td>${country.name}</td>
					<td>${country.vehicleIDs}</td>
					<td>${country.militaryPower}</td>
				`;
            countryList.appendChild(row);
        });
        countryList.style.display = "table";
    });
