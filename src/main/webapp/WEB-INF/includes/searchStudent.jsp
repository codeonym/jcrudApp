<section id="searchStudent" class="hidden flex-col">
    <form action="studentAction" method="get" class="mb-4">
        <h3 class="text-3xl font-semibold mb-2">Search Student</h3>
        <label for="searchCne" class="block mb-2">Enter Cne:</label>
        <div class="flex items-center">
            <input type="text" id="searchCne" name="searchCne" class="border border-gray-300 px-3 py-2 w-full"
                   placeholder="Enter Cne" />
            <input type="hidden" name="action" value="search_student">
            <button type="submit" class="ml-2 bg-sky-400 text-white px-4 py-2">
                Search
            </button>
        </div>
    </form>

    <table class="min-w-full border border-slate-200">
        <thead>
        <tr>
            <th class="border border-slate-200 px-4 py-2">index</th>
            <th class="border border-slate-200 px-4 py-2">Nom</th>
            <th class="border border-slate-200 px-4 py-2">Prenom</th>
            <th class="border border-slate-200 px-4 py-2">Cne</th>
            <th class="border border-slate-200 px-4 py-2">Date de naissance</th>
            <th class="border border-slate-200 px-4 py-2">Adresse</th>
            <th class="border border-slate-200 px-4 py-2">Actions</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td class="px-4 py-2"></td>
            <td class="px-4 py-2"><c:out value="${searchStudent.getNom()}" /></td>
            <td class="px-4 py-2"><c:out value="${searchStudent.getPrenom()}" /></td>
            <td class="px-4 py-2"><c:out value="${searchStudent.getCne()}" /></td>
            <td class="px-4 py-2"><c:out value="${searchStudent.getDateNaissance()}" /></td>
            <td class="px-4 py-2">
                <c:out value="${searchStudent.getAdresse()}" />
            </td>
            <td
                    class="px-4 py-2 flex flex-col lg:flex-row text-center lg:space-x-2 items-center justify-center">
                <div class="text-sky-400 border-2 border-sky-400 px-6 py-1 w-24">
                    <button onclick="showModal(<c:out value="'editSearchStudentModal_${searchStudent.getCne()}'" />)">Edit</button>
                </div>
                <form action="studentAction" method="post" class="text-red-400 border-2 border-red-400 px-6 py-1 w-24">
                    <input type="hidden" name="action" value="delete_student" />
                    <input type="hidden" name="delete_cne" value="<c:out value="${searchStudent.getCne()}" />">
                    <button type="submit" name="delete" onclick="submitForm()">Delete</button>
                </form>
                <div class="text-teal-400 border-2 border-teal-400 px-6 py-1 w-24">
                    <button onclick="showModal(<c:out value="'moduleSearchDetails_${searchStudent.getCne()}'" />)">More</button>
                </div>
            </td>
        </tr>
        </tbody>
    </table>
    <c:forEach var="student" items="${sessionScope.students}">
        <div id="editSearchStudentModal_<c:out value="${student.getCne()}" />"
             class="hidden fixed inset-0 bg-gray-900 bg-opacity-50 items-center justify-center">
            <div class="bg-white p-8 rounded shadow">
                <h2 class="text-2xl font-semibold mb-4">Edit Student</h2>
                <form action="studentAction" method="post">
                    <label for="editNom" class="block mb-2">Nom:</label>
                    <input type="text" id="editNom" name="editNom" value="<c:out value="${student.getNom()}" />"
                           class="border border-gray-300 px-3 py-2 w-full mb-4" required />

                    <label for="editPrenom" class="block mb-2">Prenom:</label>
                    <input type="text" id="editPrenom" name="editPrenom" value="<c:out value="${student.getPrenom()}" />"
                           class="border border-gray-300 px-3 py-2 w-full mb-4" required />

                    <label for="editCne" class="block mb-2">Cne:</label>
                    <input type="text" id="editCne" name="editCne" value="<c:out value="${student.getCne()}" />" class="border border-gray-300 px-3 py-2 w-full mb-4"
                           required />

                    <label for="editDateNaissance" class="block mb-2">Date De Naissance:</label>
                    <input type="date" id="editDateNaissance" name="editDateNaissance" value="<c:out value="${student.getDateNaissance()}" />" class="border border-gray-300 px-3 py-2 w-full mb-4"
                           required />

                    <label for="editAdresse" class="block mb-2">Adresse:</label>
                    <textarea id="editAdresse" name="editAdresse" value="<c:out value="${student.getAdresse()}" />"
                              class="border border-gray-300 px-3 py-2 w-full max-h-16 mb-4" required><c:out value="${student.getAdresse()}" /></textarea>
                    <input type="hidden" name="action" value="edit_student">
                    <button type="submit" class="bg-sky-400 text-white px-4 py-2">Save Changes</button>
                </form>
                <button
                        onclick="closeModal(<c:out value="'editSearchStudentModal_${student.getCne()}'" />)"
                        class="mt-4 bg-gray-300 text-gray-700 px-4 py-2 rounded">Close</button>
            </div>
        </div>
    </c:forEach>
    <c:forEach var="student" items="${sessionScope.studentsMore}">
        <div id="moduleSearchDetails_<c:out value="${student.getCne()}" />"
             class="hidden fixed inset-0 bg-gray-900 bg-opacity-50 items-center justify-center">
            <div class="bg-white p-8 rounded shadow">
                <h2 class="text-2xl font-semibold mb-4">Student Details</h2>
                <table class="min-w-full border border-gray-300">
                    <thead>
                    <tr>
                        <th class="border border-gray-300 px-4 py-2">Module</th>
                        <th class="border border-gray-300 px-4 py-2">Note</th>
                        <th class="border border-gray-300 px-4 py-2">Absence Count</th>
                    </tr>
                    </thead>
                    <tbody>
                    <!-- nested foreach to display modules -->
                    <c:forEach var="module" items="${student.getMore()}">
                        <tr>
                            <td class="border border-gray-300 px-4 py-2">
                                <c:out value="${module.getModuleNom()}" />
                            </td>
                            <td class="border border-gray-300 px-4 py-2">
                                <c:out value="${module.getModuleNote()}" />
                            </td>
                            <td class="border border-gray-300 px-4 py-2">
                                <c:out value="${module.getModuleAbsence()}" />
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <button
                        onclick="closeModal(<c:out value="'moduleSearchDetails_${student.getCne()}'" />)"
                        class="mt-4 bg-gray-300 text-gray-700 px-4 py-2 rounded">Close</button>
            </div>
        </div>
    </c:forEach>
</section>