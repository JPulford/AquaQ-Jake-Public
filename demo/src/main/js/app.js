'use strict';

// tag::vars[]
const React = require('react');
const ReactDOM = require('react-dom')
const when = require('when');
const client = require('./client');

// missing from guide
const follow = require('./follow');

const root = '/api';

// end::vars[]

// tag::app[]
class App extends React.Component {

    // top level container

    // missing parts from guide
	constructor(props) {
    	super(props);
    	this.state = {employees: [], attributes: [], pageSize: 2, links: {}};
    	this.updatePageSize = this.updatePageSize.bind(this);
    	this.onCreate = this.onCreate.bind(this);
    	this.onUpdate = this.onUpdate.bind(this);
    	this.onDelete = this.onDelete.bind(this);
    	this.onNavigate = this.onNavigate.bind(this);
    }

    // reload entire list of employees when the page size is updated
    // instead of loading employees directly from componentDidMount()
	loadFromServer(pageSize) {

	    // client is object used to make REST calls
	    // then root URI to start from
	    // third argument is array of relationships to navigate (employees) in this case with additional parameter
	    // loadFromServer is updated to fetch the collection and then use the URIs to retrieve each individual resource

        // goes to employees collection resource
    	follow(client, root, [
        		{rel: 'employees', params: {size: pageSize}}]
        	).then(employeeCollection => {
        	// fetches JSON Schema data
        		return client({
        			method: 'GET',
        			path: employeeCollection.entity._links.profile.href,
        			headers: {'Accept': 'application/schema+json'}
        		}).then(schema => {
        		// sub-then clause to store metadata and links in the App/ component
        			this.schema = schema.entity;
        			this.links = employeeCollection.entity._links;
        			return employeeCollection;
        		});
        	}).then(employeeCollection => {
        	    // converts the collection of employees into an array of GET promises to fetch
        	    // each individual resource
        	    // This is what you need to fetch an ETag header for each employee
        		return employeeCollection.entity._embedded.employees.map(employee =>
        				client({
        					method: 'GET',
        					path: employee._links.self.href
        				})
        		);
        	}).then(employeePromises => {
        	    // merges array of GET promises into single promise
        		return when.all(employeePromises);
        	}).done(employees => {
        	    // UI state updated
        		this.setState({
        			employees: employees,
        			attributes: Object.keys(this.schema.properties),
        			pageSize: pageSize,
        			links: this.links
        		});
        	});
    }

    /*loadFromServer(pageSize) {

    	    // client is object used to make REST calls
    	    // then root URI to start from
    	    // third argument is array of relationships to navigate (employees) in this case with additional parameter
        	follow(client, root, [
        		{rel: 'employees', params: {size: pageSize}}]
        	).then(employeeCollection => {
        		return client({
        			method: 'GET',
        			path: employeeCollection.entity._links.profile.href,
        			headers: {'Accept': 'application/schema+json'}
        		}).then(schema => {
        			this.schema = schema.entity;
        			return employeeCollection;
        		});
        	}).done(employeeCollection => {
        		this.setState({
        			employees: employeeCollection.entity._embedded.employees,
        			attributes: Object.keys(this.schema.properties),
        			pageSize: pageSize,
        			links: employeeCollection.entity._links});
        	});
        }*/


    /*onCreate(newEmployee) {
    	follow(client, root, ['employees']).then(employeeCollection => {
    		return client({
    			method: 'POST',
    			path: employeeCollection.entity._links.self.href,
    			entity: newEmployee,
    			headers: {'Content-Type': 'application/json'}
    		})
    	}).then(response => {
    		return follow(client, root, [
    			{rel: 'employees', params: {'size': this.state.pageSize}}]);
    	}).done(response => {
    		if (typeof response.entity._links.last != "undefined") {
    			this.onNavigate(response.entity._links.last.href);
    		} else {
    			this.onNavigate(response.entity._links.self.href);
    		}
    	});
    }*/

    onCreate(newEmployee) {
    		var self = this;
    		follow(client, root, ['employees']).then(response => {
    			return client({
    				method: 'POST',
    				path: response.entity._links.self.href,
    				entity: newEmployee,
    				headers: {'Content-Type': 'application/json'}
    			})
    		}).then(response => {
    			return follow(client, root, [{rel: 'employees', params: {'size': self.state.pageSize}}]);
    		}).done(response => {
    			if (typeof response.entity._links.last != "undefined") {
    				this.onNavigate(response.entity._links.last.href);
    			} else {
    				this.onNavigate(response.entity._links.self.href);
    			}
    		});
    }

    // when update button is pressed
    onUpdate(employee, updatedEmployee) {
    	client({
    		method: 'PUT',
    		path: employee.entity._links.self.href,
    		entity: updatedEmployee,
    		headers: {
    			'Content-Type': 'application/json',
    			'If-Match': employee.headers.Etag
    		}
    	}).done(response => {
    			this.loadFromServer(this.state.pageSize);
    	}, response => {
    		if (response.status.code === 412) {
    			alert('DENIED: Unable to update ' +
    				employee.entity._links.self.href + '. Your copy is stale.');
    		}
    	});
    }

    // when delete button is pressed. Note that the path is employee.entity, for getting the specific reference
    onDelete(employee) {
    	client({method: 'DELETE', path: employee.entity._links.self.href}).done(response => {
    		this.loadFromServer(this.state.pageSize);
    	});
    }

    // also uses the GET promises approach
    onNavigate(navUri) {
    		client({
    			method: 'GET',
    			path: navUri
    		}).then(employeeCollection => {
    			this.links = employeeCollection.entity._links;

    			return employeeCollection.entity._embedded.employees.map(employee =>
    					client({
    						method: 'GET',
    						path: employee._links.self.href
    					})
    			);
    		}).then(employeePromises => {
    			return when.all(employeePromises);
    		}).done(employees => {
    			this.setState({
    				employees: employees,
    				attributes: Object.keys(this.schema.properties),
    				pageSize: this.state.pageSize,
    				links: this.links
    			});
    		});
    }

  /*  onNavigate(navUri) {
    	client({method: 'GET', path: navUri}).done(employeeCollection => {
    		this.setState({
    			employees: employeeCollection.entity._embedded.employees,
    			attributes: this.state.attributes,
    			pageSize: this.state.pageSize,
    			links: employeeCollection.entity._links
    		});
    	});
    }*/

    updatePageSize(pageSize) {
    	if (pageSize !== this.state.pageSize) {
    		this.loadFromServer(pageSize);
    	}
    }

	componentDidMount() {

    	this.loadFromServer(this.state.pageSize);

        // previously, employees were loaded in here directly
		/*client({method: 'GET', path: '/api/employees'}).done(response => {
			this.setState({employees: response.entity._embedded.employees});
		});*/
	}

    // ensure all properties are set here
	render() {
		return (
        	<div>
        		<CreateDialog attributes={this.state.attributes} onCreate={this.onCreate}/>
        		<EmployeeList employees={this.state.employees}
        				links={this.state.links}
        				pageSize={this.state.pageSize}
        				attributes={this.state.attributes}
        				onNavigate={this.onNavigate}
        				onUpdate={this.onUpdate}
        				onDelete={this.onDelete}
        				updatePageSize={this.updatePageSize}/>
        	</div>
        )
	}
}
// end::app[]

// added this
class CreateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

	handleSubmit(e) {
		e.preventDefault();
		var newEmployee = {};
		this.props.attributes.forEach(attribute => {
			newEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		this.props.onCreate(newEmployee);

		// clear out the dialog's inputs
		// this.refs is a way to access a particular React component (virtual DOM component)
		// to get the actual DOM element you need to use React.findDOMNode()
		this.props.attributes.forEach(attribute => {
			ReactDOM.findDOMNode(this.refs[attribute]).value = '';
		});

		// Navigate away from the dialog to hide it.
		window.location = "#";
	}

    // Display component
    // converts the array of JSON Schema attributes into an array of HTML inputs
	render() {
		var inputs = this.props.attributes.map(attribute =>
			<p key={attribute}>
				<input type="text" placeholder={attribute} ref={attribute} className="field" />
			</p>
		);

        // <div> is anchor tag for the button to open the dialog
        // here, the button created is 'Create' for adding a new employee
        // {inputs} = dynamic list of input fields, which are injected following by the create button
		return (
			<div>
				<a href="#createEmployee">Create</a>

				<div id="createEmployee" className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Create new employee</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Create</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
}

// end added

// For adding existing employee records
class UpdateDialog extends React.Component {

	constructor(props) {
		super(props);
		this.handleSubmit = this.handleSubmit.bind(this);
	}

    // uses React refs
	handleSubmit(e) {
		e.preventDefault();
		var updatedEmployee = {};
		this.props.attributes.forEach(attribute => {
			updatedEmployee[attribute] = ReactDOM.findDOMNode(this.refs[attribute]).value.trim();
		});
		// after input values are extracted and loaded into the updatedEmployee object
		// the top level onUpdate() method is invoked
		this.props.onUpdate(this.props.employee, updatedEmployee);
		window.location = "#";
	}

    // load fields with 'this.props.employee'
	render() {
		var inputs = this.props.attributes.map(attribute =>
				<p key={this.props.employee.entity[attribute]}>
					<input type="text" placeholder={attribute}
						   defaultValue={this.props.employee.entity[attribute]}
						   ref={attribute} className="field" />
				</p>
		);

		var dialogId = "updateEmployee-" + this.props.employee.entity._links.self.href;

		return (
			<div key={this.props.employee.entity._links.self.href}>
				<a href={"#" + dialogId}>Update</a>
				<div id={dialogId} className="modalDialog">
					<div>
						<a href="#" title="Close" className="close">X</a>

						<h2>Update an employee</h2>

						<form>
							{inputs}
							<button onClick={this.handleSubmit}>Update</button>
						</form>
					</div>
				</div>
			</div>
		)
	}
};


// tag::employee-list[]
class EmployeeList extends React.Component{

    constructor(props) {
    	super(props);
    	this.handleNavFirst = this.handleNavFirst.bind(this);
    	this.handleNavPrev = this.handleNavPrev.bind(this);
    	this.handleNavNext = this.handleNavNext.bind(this);
    	this.handleNavLast = this.handleNavLast.bind(this);
    	this.handleInput = this.handleInput.bind(this);
    }

    // button handlers
	handleInput(e) {
    	e.preventDefault();
    	var pageSize = ReactDOM.findDOMNode(this.refs.pageSize).value;
    	if (/^[0-9]+$/.test(pageSize)) {
    		this.props.updatePageSize(pageSize);
    	} else {
    		ReactDOM.findDOMNode(this.refs.pageSize).value =
    			pageSize.substring(0, pageSize.length - 1);
    	}
    }

    // pressed, navigates to first href (first employee stored)

	handleNavFirst(e){
        e.preventDefault();
        this.props.onNavigate(this.props.links.first.href);
    }

    handleNavPrev(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.prev.href);
    }

    handleNavNext(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.next.href);
    }

    handleNavLast(e) {
        e.preventDefault();
        this.props.onNavigate(this.props.links.last.href);
    }

// The employee state data is included in creation of the <EmployeeList /> React component as an input parameter

	render() {
		var employees = this.props.employees.map(employee =>
        		<Employee key={employee.entity._links.self.href}
                			    employee={employee}
                				attributes={this.props.attributes}
                				onUpdate={this.props.onUpdate}
                                onDelete={this.props.onDelete}/>
        	);
        	//<Employee key={employee._links.self.href} employee={employee} onDelete={this.props.onDelete}/>

        	var navLinks = [];
        	if ("first" in this.props.links) {
        		navLinks.push(<button key="first" onClick={this.handleNavFirst}>&lt;&lt;</button>);
        	}
        	if ("prev" in this.props.links) {
        		navLinks.push(<button key="prev" onClick={this.handleNavPrev}>&lt;</button>);
        	}
        	if ("next" in this.props.links) {
        		navLinks.push(<button key="next" onClick={this.handleNavNext}>&gt;</button>);
        	}
        	if ("last" in this.props.links) {
        		navLinks.push(<button key="last" onClick={this.handleNavLast}>&gt;&gt;</button>);
        	}

            // HTML table wrapped around the array of employees
            // Table headings
        	return (
        		<div>
        			<input ref="pageSize" defaultValue={this.props.pageSize} onInput={this.handleInput}/>
        			<table>
        				<tbody>
        					<tr>
        						<th>First Name</th>
        						<th>Last Name</th>
        						<th>Description</th>
        						<th></th>
        						<th></th>
        					</tr>
        					{employees}
        				</tbody>
        			</table>
        			<div>
        				{navLinks}
        			</div>
        		</div>
        	)
	}


}
// end::employee-list[]

// tag::employee[]
class Employee extends React.Component {

	constructor(props) {
		super(props);
		this.handleDelete = this.handleDelete.bind(this);
	}

	handleDelete() {
		this.props.onDelete(this.props.employee);
	}

	render() {
		return (
			<tr>
				<td>{this.props.employee.entity.firstName}</td>
				<td>{this.props.employee.entity.lastName}</td>
				<td>{this.props.employee.entity.description}</td>
				<td>
					<UpdateDialog employee={this.props.employee}
								  attributes={this.props.attributes}
								  onUpdate={this.props.onUpdate}/>
				</td>
				<td>
					<button onClick={this.handleDelete}>Delete</button>
				</td>
			</tr>
		)
	}
}
/*class Employee extends React.Component{

    // has a single HTML table row wrapped around the employee's three properties
    // where the property is this.props.employee

	constructor(props) {
    		super(props);
    		this.handleDelete = this.handleDelete.bind(this);
    	}

    	handleDelete() {
    		this.props.onDelete(this.props.employee);
    	}

    	render() {
    		return (
    			<tr>
    				<td>{this.props.employee.firstName}</td>
    				<td>{this.props.employee.lastName}</td>
    				<td>{this.props.employee.description}</td>
    				<td>
    					<button onClick={this.handleDelete}>Delete</button>
    				</td>
    			</tr>
    		)
    	}
}*/
// end::employee[]

// tag::render[]

// Render everything
// first argument is the React component (App)
// second argument is the DOM node to inject into it, as defined in the HTML page: <div id="react"></div>
ReactDOM.render(
	<App />,
	document.getElementById('react')
)
// end::render[]

