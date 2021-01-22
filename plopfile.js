const path = require('path');

module.exports = function (plop) {

	plop.addHelper('currentDir', function () {
		return path.normalize(process.env["INIT_CWD"] || process.cwd());
	});

	plop.addHelper('basePackage', function() {
		const cwd = path.normalize(process.env["INIT_CWD"] || process.cwd());
		const searchPattern = 'java/'
		if (cwd.indexOf(searchPattern) == -1) {
			throw "Cannot find a package-like directory structure. Are you in a valid directory?"
		}

		const substringStart = cwd.indexOf(searchPattern) + searchPattern.length
		const package = cwd.substring(substringStart).replace(/\//g, ".")

		return package;
	})

	plop.setGenerator('mvi', {
		description: 'MVI boilerplate generator',
		prompts: [{
			type: 'input',
			name: 'name',
			message: 'Screen name:'
		}],
		actions: [
			{
				type: "addMany",
				destination: '{{currentDir}}/{{camelCase name}}',
				base: `.plop-templates/mvi`,
				templateFiles: `.plop-templates/mvi/*.hbs`
			  }
		]
	});
};
