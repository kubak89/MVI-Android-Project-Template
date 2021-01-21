const path = require('path');

module.exports = function (plop) {

	plop.addHelper('currentDir', function () {
		return path.normalize(process.env["INIT_CWD"] || process.cwd());
	});

	plop.addHelper('basePackage', function() {
		const cwd = path.normalize(process.env["INIT_CWD"] || process.cwd());
		const substringStart = cwd.indexOf('java/') + 5
		const package = cwd.substring(substringStart).replaceAll('/', '.')

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
