<section id="addNotes" class="hidden">
    <form action="studentAction" method="post" class="mt-8">
        <h3 class="text-xl font-semibold mb-4">Add Notes</h3>

        <label for="noteCne" class="block mb-2">Student CNE:</label>
        <input type="text" id="noteCne" name="noteCne" class="border border-gray-300 px-3 py-2 w-full mb-4"
               placeholder="Enter Student CNE" required />

        <label class="block mb-2">Notes for Six Modules:</label>
        <div class="flex flex-wrap -mx-2">
            <c:forEach var="module" items="${sessionScope.modules}">
                <div class="w-1/2 px-2 mb-4">
                    <label for="module_<c:out value="${module.getModuleNom()}" />" class="block mb-2"><c:out value="${module.getModuleNom()}" />:</label>
                    <input type="number" id="module_<c:out value="${module.getModuleNom()}" />" name="module_<c:out value="${module.getModuleId()}" />" class="border border-gray-300 px-3 py-2 w-full" min="0"
                           max="20" required />
                </div>
            </c:forEach>
        </div>

        <input type="hidden" name="action" value="notes_student" />
        <button type="submit" class="bg-teal-400 text-white px-4 py-2">
            Add Notes
        </button>
    </form>
</section>