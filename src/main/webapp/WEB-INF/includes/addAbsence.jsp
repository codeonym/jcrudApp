<section id="addAbsence" class="hidden">
    <form action="studentAction" method="post" class="mt-8">
        <h3 class="text-xl font-semibold mb-4">Add Absence</h3>

        <label for="absenceModule" class="block mb-2">Select Module:</label>
        <select id="absenceModule" name="absenceModule" class="border border-gray-300 px-3 py-2 w-full mb-4" required>
            <option value="" disabled selected>Select Module</option>
            <c:forEach var="module" items="${sessionScope.modules}">
                <option  value="<c:out value="${module.getModuleId()}" />"><c:out value="${module.getModuleNom()}" /></option>
            </c:forEach>
        </select>

        <label for="absenceCne" class="block mb-2">Student Cne:</label>
        <input type="text" id="absenceCne" name="absenceCne" class="border border-gray-300 px-3 py-2 w-full mb-4"
               placeholder="Enter Student CNE" required />
        <label for="absenceDate" class="block mb-2">Select Date:</label>
        <input type="datetime-local" id="absenceDate" name="absenceDate"
               class="border border-gray-300 px-3 py-2 w-full mb-4" required />
        <input type="hidden" name="action" value="absence_student" />
        <button type="submit" class="bg-teal-500 text-white px-4 py-2">
            Add Absence
        </button>
    </form>
</section>