## Template for Validation

After reading the articles about validation, there might be some settings that we want to keep as a template.
This is what this directory aims for.

Here's a list of what is included and the classes that provide the functionality.
The classes are in bracket (`class`)
1. Validation failed messages rendering. Modify this to suit your project format (`ExceptionHandlingControllerAdvice`).
2. `Empty string` submitted is turned to `null`.
   * Handles the `JSON` requests (`BeanConfig`).
   * Handles the `form data` requests (`BindingControllerAdvice`).
3. Customize i18n message location, set to load message resource `.properties` as `UTF-8`.
This might not be needed, if so just not to include it (`MyWebMvcConfigurer`).

That's it :)
