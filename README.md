# Just learning Maven MultiModule
rest service
___

yanacona - dummy project
- Dao does not need import module as a dependency
- Repository needs dao
- Converter needs dao
- Web needs repository and converter
- App needs web and repository